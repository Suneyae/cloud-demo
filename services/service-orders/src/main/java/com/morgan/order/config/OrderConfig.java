package com.morgan.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration //表明这是一个配置类，Spring 会在启动时加载它，并将其中定义的 Bean 注册到 Spring 容器中
public class OrderConfig {

    @LoadBalanced //添加这个注解，启用客户端负责均衡，那么RestTemplate的getForObject方法就会是负载均衡的
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
