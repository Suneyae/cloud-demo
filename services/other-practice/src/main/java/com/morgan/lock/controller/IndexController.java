package com.morgan.lock.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * ClassName: IndexController
 * Package: com.morgan.lock.controller
 *
 * @author Morgan Wei
 * @create 2026-05-21-22:27
 * @Description:
 */

@RestController
@Slf4j
public class IndexController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final Object lock = new Object();
    @PostMapping("/deductStock")
    public String deductStock(){
        synchronized (lock){
            String stockValue = stringRedisTemplate.opsForValue().get("stock");
            int stocks = Objects.isNull(stockValue) ? 0 : Integer.parseInt(stockValue);
            if (stocks>0){
                int leftStocks = stocks - 1;
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(leftStocks));
                log.info("thread {}, stocks number is {}",Thread.currentThread().getName(), leftStocks);
            } else {
                log.info("no stocks left");
            }
        }


        return "end";
    }

    @GetMapping("/deductStock")
    public String deductStocks(){
        synchronized (lock){
            String stockValue = stringRedisTemplate.opsForValue().get("stock");
            int stocks = Objects.isNull(stockValue) ? 0 : Integer.parseInt(stockValue);
            if (stocks>0){
                int leftStocks = stocks - 1;
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(leftStocks));
                log.info("thread {}, stocks number is {}",Thread.currentThread().getName(), leftStocks);
            } else {
                log.info("no stocks left");
            }
        }


        return "end";
    }

    public static void main(String[] args) {
        // 启动2个线程模拟调用deductStock接口
        for (int i = 0; i < 40; i++) {
            new Thread(() -> {
                try {
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) new java.net.URL("http://localhost:8090/deductStock").openConnection();
                    conn.setRequestMethod("POST");
                    conn.getResponseCode();
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        //

    }
}
