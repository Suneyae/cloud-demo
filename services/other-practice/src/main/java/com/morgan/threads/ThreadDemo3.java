package com.morgan.threads;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: ThreadDemo
 * Package: com.morgan.threads
 *
 * @author Morgan Wei
 * @create 2026-04-29-22:09
 * @Description:
 */
@Slf4j
public class ThreadDemo3 extends Thread {
    // 中断线程
        public static void main(String[] args) throws InterruptedException {
            Thread t = new MyThread();
            t.start();
            Thread.sleep(1000);
            t.interrupt(); // 中断t线程
            t.join(); // 等待t线程结束
            System.out.println("end");
        }
    }

    class MyThread extends Thread {
        public void run() {
            Thread hello = new HelloThread();
            hello.start(); // 启动hello线程
            try {
                hello.join(); // 等待hello线程结束
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            }
            hello.interrupt();
        }
    }

    class HelloThread extends Thread {
        public void run() {
            int n = 0;
            while (!isInterrupted()) {
                n++;
                System.out.println(n + " hello!");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
