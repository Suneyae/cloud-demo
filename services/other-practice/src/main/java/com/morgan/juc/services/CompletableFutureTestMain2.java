package com.morgan.juc.services;

import com.morgan.juc.client.PublicApiClient;
import com.morgan.juc.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.*;

/**
 * ClassName: CompletableFutureTestMain2
 * Package: com.morgan.juc.services
 *
 * @author Morgan Wei
 * @create 2026-05-24-12:03
 * @Description:
 */
@Slf4j
public class CompletableFutureTestMain2 {

    public static void main(String[] args) {
//        SimulateNetworkReqest();
        temp();
    }

    private static void temp(){
        ExecutorService executor = Executors.newFixedThreadPool(5);


        // 串行编排：先获取用户ID → 再根据ID查用户信息 → 最后打印信息
        CompletableFuture<String> userIdFuture = CompletableFuture.supplyAsync(() -> "1001", executor);
        CompletableFuture<String> userInfoFuture = userIdFuture.thenApplyAsync(userId -> {
            return "用户" + userId + "：张三"; // 模拟查用户信息
        }, executor);
        userInfoFuture.thenAccept(info -> System.out.println("最终结果：" + info)); // 消费结果

        executor.shutdown();
    }

    private static void SimulateNetworkReqest() {
        PublicApiClient publicApiClient = new PublicApiClient(new RestTemplate());
        ExecutorService executors = Executors.newFixedThreadPool(5);
        CompletableFuture<Post> completableFuture = CompletableFuture.supplyAsync(()->{
            log.info("启动异步任务 thread {} ",Thread.currentThread().getName());
            return publicApiClient.getPostAsObject();
        },executors);

        try{

            Post post = completableFuture.orTimeout(2, TimeUnit.SECONDS).join();
            log.info("post is {}",post);

        }catch(CompletionException e){
            // 处理超时或其他异常情况，比如记录日志
            Throwable throwable = e.getCause();
            log.info("启动异步任务 异常 {} ",throwable.getMessage());
            if(throwable instanceof TimeoutException){
                log.info("网络请求超时 {}", throwable.toString());
            }


        } finally{
            executors.shutdown();
        }
    }
}
