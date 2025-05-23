package com.morgan.order.service.impl;

import com.morgan.order.bean.Order;
import com.morgan.order.service.OrderService;
import com.morgan.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    DiscoveryClient discoveryClient;//这是spring cloud自带的服务发现API


    @Autowired
    RestTemplate restTemplate;

    @Override
    public Order createOrder(Long prodId, Long userId) {
        Product product = getProductFromRemote(prodId);
        Order order = new Order();
        order.setId(0L);
        //Todo 应该是算出来的
        BigDecimal totalAmt = product.getPrice().multiply(new BigDecimal(product.getNum()));
        order.setTotalAmount(totalAmt);

        order.setUserId(userId);
        order.setNickName("abc");
        order.setAddress("chengdu");

        //Todo 应该远程调用的
        order.setProductList(Arrays.asList(product));
        return order;
    }

    private Product getProductFromRemote(Long productId){
        //1. 获取到商品服务所在的所有机器IP+Port
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance instance = instances.get(0);
        int port = instance.getPort();
        //远程URL,sample http://localhost:9000/getProd/3333
        String url = "http://" + instance.getHost() + ":" +port + "/getProd/"+productId;

        log.info("远程请求:{}",url);
        log.info("远程请求xx",url);
        //2. 给远程服务发送请求
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }
}
