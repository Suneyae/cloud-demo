package com.morgan.threads;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ClassName: FutureTaskDemo
 * Package: com.morgan.threads
 *
 * @author Morgan Wei
 * @create 2026-05-02-10:38
 * @Description:
 */
@Slf4j
public class FutureTaskDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        extractWithReturn();

    }

    private static void extractWithoutReturn(){
        Runnable runnable = () -> {
            log.info("runnable 正在执行");
        };

        FutureTask<Void> futureTask = new FutureTask<>(runnable,null);

        new Thread(futureTask).start();
    }

    private static void extractWithReturn() throws InterruptedException, ExecutionException {
        // 1. 定义Callable任务：有返回值
        Callable<Integer> callable = () -> {
            Thread.sleep(1000);
            return 20000;
        };

        // 2. 包装为FutureTask
        FutureTask<Integer> myFutureTask = new FutureTask<>(callable);

        // 3. 给线程执行
        new Thread(myFutureTask).start();
        log.info("正在执行{}",Thread.currentThread().getName());

        // 4. 获取阻塞结果
        Integer result = myFutureTask.get();
        log.info("任务结果: {}",result);
    }
}
