package com.morgan.controller;

import com.morgan.event.OrderCreateEvent;
import com.morgan.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: OrderController
 * Package: com.morgan.controller
 *
 * @author Morgan Wei
 * @create 2026-11-13-18:00
 * @Description:
 */
@RestController
public class OrderController {

//    @Autowired
//    ApplicationEventPublisher publisher;

    @Autowired
    OrderService orderService;

    @PostMapping("/createOrder")
    public String createOrder(@RequestParam String orderName){
        orderService.order(orderName);
//        publisher.publishEvent(new OrderCreateEvent(this,orderName));

        return "created Order ok";
    }

}
