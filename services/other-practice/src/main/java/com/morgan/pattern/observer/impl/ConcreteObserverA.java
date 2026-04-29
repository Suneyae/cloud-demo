package com.morgan.pattern.observer.impl;

import com.morgan.pattern.observer.Observer;

/**
 * ClassName: ConcreteObserverA
 * Package: com.morgan.pattern.observer.impl
 *
 * @author Morgan Wei
 * @create 2025-12-28-11:59
 * @Description:
 */
public class ConcreteObserverA implements Observer {

    @Override
    public void update(String state) {
        System.out.println("观察者A收到通知，状态变为：" + state);
    }


}
