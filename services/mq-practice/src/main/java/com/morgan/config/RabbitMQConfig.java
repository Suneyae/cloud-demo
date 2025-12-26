package com.morgan.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: RabbitMQConfig
 * Package: com.morgan.com.config
 *
 * @author Morgan Wei
 * @create 2025-12-26-21:41
 * @Description:
 */
@Configuration
public class RabbitMQConfig {

    // 1. 定义队列名称
    public static final String TEST_QUEUE_NAME = "test_queue";
    // 2. 定义交换机名称
    public static final String TEST_EXCHANGE_NAME = "test_exchange";
    // 3. 定义路由键
    public static final String TEST_ROUTING_KEY = "test_routing_key";

    // 创建队列（持久化、非排他、非自动删除）
    @Bean
    public Queue testQueue() {
        return new Queue(TEST_QUEUE_NAME, true, false, false);
    }

    // 创建直连交换机（持久化、非自动删除）
    @Bean
    public DirectExchange testExchange() {
        return new DirectExchange(TEST_EXCHANGE_NAME, true, false);
    }

    // 绑定队列到交换机（指定路由键）
    @Bean
    public Binding bindingTestQueue(Queue testQueue, DirectExchange testExchange) {
        return BindingBuilder.bind(testQueue).to(testExchange).with(TEST_ROUTING_KEY);
    }
}