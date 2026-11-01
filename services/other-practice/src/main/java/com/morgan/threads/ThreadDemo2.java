package com.morgan.threads;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * ClassName: ThreadDemo
 * Package: com.morgan.threads
 *
 * @author Morgan Wei
 * @create 2026-04-29-22:09
 * @Description:
 */
@Slf4j
public class ThreadDemo2 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            log.info("{}, 当前正在执行任务...", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
//        ThreadDemo2 threadDemo2 = new ThreadDemo2();
//        threadDemo2.start();
//
//        for (int i = 0; i < 5; i++) {
//            log.info("{}, 当前正在执行main方法...", Thread.currentThread().getName());
//        }


        Thread thread = new Thread(() -> {
            log.info("正在执行...{}", Thread.currentThread().getName());
            while(true){

            }
        });


        thread.setName("线程1");
        //守护线程
        thread.setDaemon(true);
        thread.start();

        log.info("over...{}", Thread.currentThread().getName());


        StringBuffer sbf;
        StringBuilder sbd;
        String str;

    }
}
