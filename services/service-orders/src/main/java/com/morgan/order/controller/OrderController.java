package com.morgan.order.controller;

import com.morgan.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "create order",description = "create order by calling remote product service")
    public com.morgan.order.bean.Order createOrder(@Parameter(description="商品id") @PathParam("prodId") Long prodId, @Parameter(description = "用户id")@PathParam("userId") Long userId){
        Order order = orderService.createOrder(prodId,userId);
        return order;
    }
}
