package com.morgan.listener;

import com.morgan.event.OrderCreateEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * ClassName: OrderLister
 * Package: com.morgan.listener
 *
 * @author Morgan Wei
 * @create 2026-11-13-17:56
 * @Description:
 */
@Component
public class OrderLister{

    //listen the event
    @Order(1)
    @EventListener
    public void listenOrder(OrderCreateEvent event){
        System.out.println("orderListener:"+event.getOrderName()+",amount:"+event.getOrderAmount());
    }

    @Order(2)
    @EventListener(condition = "#event.orderAmount>100")
    public void listenOrder2(OrderCreateEvent event){
        System.out.println("orderListener2, amount>100:"+event.getOrderName()+",amount:"+event.getOrderAmount());
    }


}
