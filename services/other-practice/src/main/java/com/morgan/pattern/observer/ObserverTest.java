package com.morgan.pattern.observer;

import com.morgan.pattern.observer.impl.ConcreteObserverA;
import com.morgan.pattern.observer.impl.ConcreteObserverB;
import com.morgan.pattern.observer.impl.ConcreteSubject;

/**
 * ClassName: ObserverTest
 * Package: com.morgan.pattern.observer
 *
 * @author Morgan Wei
 * @create 2025-12-28-12:01
 * @Description:
 */
public class ObserverTest {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();
        subject.registerObserver(new ConcreteObserverA());
        subject.registerObserver(new ConcreteObserverB());

        subject.setState("已更新");
        // 输出：
        // 观察者A收到通知，状态变为：已更新
        // 观察者B收到通知，状态变为：已更新
    }
}
