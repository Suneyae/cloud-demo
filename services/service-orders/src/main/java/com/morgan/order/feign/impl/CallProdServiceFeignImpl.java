package com.morgan.order.feign.impl;

import com.morgan.order.feign.CallProdServiceFeign;
import com.morgan.product.bean.Product;
import org.springframework.stereotype.Component;


@Component
public class CallProdServiceFeignImpl implements CallProdServiceFeign {


    @Override
    public Product getProductById(Long prodId) {
        Product product = new Product();
        product.setProductName("失败订单,服务降级");
        return product;
    }
}
