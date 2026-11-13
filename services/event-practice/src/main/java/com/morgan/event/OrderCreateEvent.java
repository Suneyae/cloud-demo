package com.morgan.event;

import org.springframework.context.ApplicationEvent;

/**
 * ClassName: OrderCreateEvent
 * Package: com.morgan.event
 *
 * @author Morgan Wei
 * @create 2026-11-13-17:53
 * @Description:
 */
public class OrderCreateEvent extends ApplicationEvent {
    String orderName;

    public String getOrderName() {
        return orderName;
    }

    public OrderCreateEvent(Object source,String orderName) {
        super(source);
        this.orderName = orderName;
    }

}
