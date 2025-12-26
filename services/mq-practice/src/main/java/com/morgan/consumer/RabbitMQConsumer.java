package com.morgan.consumer;

import com.morgan.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName: RabbitMQConsumer
 * Package: com.morgan.consumer
 *
 * @author Morgan Wei
 * @create 2025-12-26-21:44
 * @Description:
 */
@Component
public class RabbitMQConsumer {

    // 监听指定队列：当队列有消息时，自动触发该方法
    @RabbitListener(queues = RabbitMQConfig.TEST_QUEUE_NAME)
    public void receiveMessage(String msg) {
        System.out.println("消费者接收到消息：" + msg);
        // 这里可添加业务逻辑（如入库、调用接口等）
    }
}