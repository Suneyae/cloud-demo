package com.morgan.lock.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author morganwei
 * @date 26/06/2025 10:27
 * @description TODO
 */
@Service
public class LockService {

    @Autowired
    private RedissonClient redissonClient;
    public String doBusinessWithLock() {
        RLock lock = redissonClient.getLock("my-lock");
        try {
            // 尝试加锁，最多等待5秒，上锁后10秒自动释放
            if (lock.tryLock(5, 10, TimeUnit.SECONDS)) {
                try {
                    // 模拟业务逻辑
                    Thread.sleep(3000);
                    return "业务处理成功";
                } finally {
                    lock.unlock();
                }
            } else {
                return "获取锁失败，请稍后重试";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "线程中断";
        }
    }

}
