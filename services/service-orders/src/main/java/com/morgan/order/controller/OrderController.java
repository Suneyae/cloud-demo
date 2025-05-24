package com.morgan.order.controller;

import com.morgan.order.config.ExpireField;
import com.morgan.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.morgan.order.bean.Order;

@RefreshScope //开启placeholder 配置属性的自动刷新功能
@RestController
public class OrderController {


    @Autowired
    OrderService orderService;

    @Value("${expire.time}")
    int expireTime;

    @Value("${expire.time_unit}")
    String expireUnit;

    @Autowired
    ExpireField expireField;


    @GetMapping("/create")
    @Operation(summary = "create order",description = "create order by calling remote product service")
    public com.morgan.order.bean.Order createOrder(@Parameter(description="商品id") @PathParam("prodId") Long prodId, @Parameter(description = "用户id")@PathParam("userId") Long userId){
        Order order = orderService.createOrder(prodId,userId);
        return order;
    }


    //读取配置中心的设置
    @GetMapping("/getConfig")
    public String getConfig(){
        //没有自动刷新，所以注释掉
//        return "expire time is "+expireTime+expireUnit;
        //测试发现，即使配合上 RefreshScope 和 ConfigurationProperties 注解，也还是没有实现自动刷新，
        return "expire time from expireField,"+expireField.getTime()+expireField.getTimeUnit();
    }
}
