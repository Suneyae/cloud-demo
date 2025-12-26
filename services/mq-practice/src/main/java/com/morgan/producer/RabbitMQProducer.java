package com.morgan.producer;

/**
 * ClassName: RabbitMQProducer
 * Package: com.morgan.producer
 *
 * @author Morgan Wei
 * @create 2025-12-26-21:43
 * @Description:
 */

import com.morgan.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息生产者：发送消息到RabbitMQ
 */
@RestController
public class RabbitMQProducer {

    // 注入RabbitTemplate（Spring提供的RabbitMQ操作模板）
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试接口：发送消息
     * 访问地址：http://localhost:8080/sendMsg/你要发送的内容
     */
    @GetMapping("/sendMsg/{msg}")
    public String sendMessage(@PathVariable String msg) {
        try {
            // 发送消息：参数（交换机名称、路由键、消息内容）
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TEST_EXCHANGE_NAME,
                    RabbitMQConfig.TEST_ROUTING_KEY,
                    msg
            );
            return "消息发送成功：" + msg;
        } catch (Exception e) {
            return "消息发送失败：" + e.getMessage();
        }
    }
}