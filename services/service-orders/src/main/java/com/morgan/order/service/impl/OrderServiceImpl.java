package com.morgan.order.service.impl;

import com.morgan.order.bean.Order;
import com.morgan.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(Long prodId, Long userId) {
        Order order = new Order();
        order.setId(0L);
        //Todo 应该是算出来的
        order.setTotalAmount(new BigDecimal(0));

        order.setUserId(userId);
        order.setNickName("abc");
        order.setAddress("chengdu");

        //Todo 应该远程调用的
        order.setProductList(null);
        return order;
    }
}
