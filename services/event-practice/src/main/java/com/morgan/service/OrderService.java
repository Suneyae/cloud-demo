package com.morgan.service;

import com.morgan.event.OrderCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

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
}
