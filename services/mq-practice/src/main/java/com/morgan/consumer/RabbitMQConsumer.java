package com.morgan.consumer;

import com.morgan.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
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
    @RabbitListener(queues = {RabbitMQConfig.TEST_QUEUE_NAME,RabbitMQConfig.TEST_QUEUE_NAME2})
    public void receiveMessage(String msg, Message rawMessage) {
        System.out.println("消费者接收到消息：" + msg);
        // 这里可添加业务逻辑（如入库、调用接口等）
        // 打印消息的投递标签（唯一标识），证明是RabbitMQ正常投递的消息
        System.out.println("消息投递标签：" + rawMessage.getMessageProperties().getDeliveryTag());
        // Spring AMQP默认自动确认，只要方法不抛异常，消息会自动被确认
    }
}