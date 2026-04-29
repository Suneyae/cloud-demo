package com.morgan.pattern.observer;

/**
 * ClassName: Observer
 * Package: com.morgan.pattern
 *
 * @author Morgan Wei
 * @create 2025-12-28-11:53
 * @Description:
 */
public interface Observer {
    void update(String state); // 收到通知后更新
}
