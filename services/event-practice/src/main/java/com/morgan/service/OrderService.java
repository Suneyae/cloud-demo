package com.morgan.service;

import com.morgan.event.OrderCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * ClassName: OrderService
 * Package: com.morgan.service
 *
 * @author Morgan Wei
 * @create 2026-11-13-17:59
 * @Description:
 */
@Service
public class OrderService {
    @Autowired
    ApplicationEventPublisher publisher;
    public void order(String orderName, BigDecimal orderAmount){
        publisher.publishEvent(new OrderCreateEvent(orderName,orderAmount));
        System.out.println("order crated in service logic");
    }

    /**
     * 添加事务业务
     * day03 part of https://www.yuque.com/woyouxiaomianaola/bp5ofg/xbhagbpvfqxh76as#Fjxrs
     * @param orderName
     * @param throwErr
     */
    @Transactional
    public void orderTxn(String orderName,BigDecimal amount, boolean throwErr){
        // 诊断：打印当前是否处于激活的事务内
        boolean hasTx = org.springframework.transaction.support.TransactionSynchronizationManager.isActualTransactionActive();
        System.out.println("【诊断】isActualTransactionActive = " + hasTx);
        publisher.publishEvent(new OrderCreateEvent(orderName, amount));
        if(throwErr){
            System.out.println("主动抛出异常...");
            throw new RuntimeException("模拟抛出异常");
        }
    }

}
