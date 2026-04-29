package com.morgan.threads;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: ThreadDemo
 * Package: com.morgan.threads
 *
 * @author Morgan Wei
 * @create 2026-04-29-22:09
 * @Description:
 */
@Slf4j
public class ThreadDemo {
    public static void main(String[] args) {
//        // 1. 创建固定 10 线程的线程池
//        ExecutorService executor  = Executors.newFixedThreadPool(10);
//        // 2. 提交 20 个任务（最多同时跑 10 个）
////        for ( int i =1; i<=20; i++){
////            int taskNum = i;
////            executor.submit(() ->{
////               log.info("正在执行:{}", taskNum);
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    Thread.currentThread().interrupt();
////                    throw new RuntimeException(e);
////                }
////            });
////        }
//        test01(executor);
//        // 3. 关闭线程池（必须！）
//        executor.shutdown();

        // 主线程执行任务 -1
        mainTask();

        // 子线程执行任务

        CompletableFuture<Void> asyncTask = CompletableFuture.runAsync(() -> {
            log.info("子线程开始耗时任务,{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
            try {
                Thread.sleep(12000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            log.info("子线程结束耗时任务,{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        });
        // 主线程执行任务 -2
        mainTask2();

        // 如果需要等待子线程完成再结束程序
        asyncTask.join();
    }

    //异步编程 CompletableFuture

    private static void test01(ExecutorService executor){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    LocalDateTime now = LocalDateTime.now();

                    // return string user with time
                    return "user -" + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
                }, executor);

        // 链式调用,处理成功和失败情况
        future.thenApply(result -> {
                    log.info("原始结果: {}", result);
                    return result.toUpperCase(); // 可以继续转换
                })
                .exceptionally(ex -> {
                    log.error("发生异常: {}", ex.getMessage());
                    return "error";
                })
                .thenAccept(finalResult -> {
                    log.info("最终结果: {}", finalResult);
                });
    }

    private static void mainTask(){
        for ( int i=1; i<=5; i++){
            log.info("main thread {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void mainTask2(){
        for ( int i=1; i<=5; i++){
            log.info("main thread2 {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
