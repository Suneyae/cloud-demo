package com.morgan.order.service;

import com.morgan.order.bean.Order;

public interface OrderService {
    Order createOrder(Long prodId,Long userId);
}
