package com.morgan.listener;

import com.morgan.event.OrderCreateEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listenerTxn(OrderCreateEvent event){
        System.out.println("order 事务提交了..., 写日志");

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void listenerTxn2(OrderCreateEvent event){
        System.out.println("order 事务回滚了..., 发短信通知用户");

    }


}
