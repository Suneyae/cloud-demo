package com.morgan.order.controller;

import com.morgan.order.service.OrderService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.morgan.order.bean.Order;

@RestController
public class OrderController {


    @Autowired
    OrderService orderService;

    @GetMapping("/create")
    public com.morgan.order.bean.Order createOrder(@PathParam("prodId") Long prodId, @PathParam("userId") Long userId){
        Order order = orderService.createOrder(prodId,userId);
        return order;
    }
}
