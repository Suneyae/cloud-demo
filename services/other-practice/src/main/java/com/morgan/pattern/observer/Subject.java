package com.morgan.pattern.observer;

/**
 * ClassName: Subject
 * Package: com.morgan.pattern.observer
 *
 * @author Morgan Wei
 * @create 2025-12-28-11:56
 * @Description:
 */
public interface Subject {
    void registerObserver(Observer observer); // 注册观察者
    void removeObserver(Observer observer); // 移除观察者
    void notifyObservers(); // 通知所有观察者
}
