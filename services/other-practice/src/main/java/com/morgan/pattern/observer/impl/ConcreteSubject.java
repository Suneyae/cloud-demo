package com.morgan.pattern.observer.impl;

import com.morgan.pattern.observer.Observer;
import com.morgan.pattern.observer.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ConcreteSubject
 * Package: com.morgan.pattern.observer.impl
 *
 * @author Morgan Wei
 * @create 2025-12-28-11:57
 * @Description:
 */
public class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String state; // 主题状态

    public void setState(String state) {
        this.state = state;
        notifyObservers(); // 状态变化，通知观察者
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(state);
        }
    }
}
