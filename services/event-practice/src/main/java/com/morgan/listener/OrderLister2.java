package com.morgan.listener;

import com.morgan.event.OrderCreateEvent;
import org.springframework.context.ApplicationListener;
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
public class OrderLister2 implements ApplicationListener<OrderCreateEvent> {

    @Override
    public void onApplicationEvent(OrderCreateEvent event) {
//        if(event instanceof OrderCreateEvent){
//            OrderCreateEvent orderCreateEvent = (OrderCreateEvent)event;
//            String orderNm = orderCreateEvent.getOrderName();
//            System.out.println("onApplicationEvent created "+orderNm);
//        }
        String orderNm = event.getOrderName();
        System.out.println("监听到order ,发送短信。。。"+orderNm);
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
