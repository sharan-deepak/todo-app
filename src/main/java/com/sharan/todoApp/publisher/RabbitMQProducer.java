package com.sharan.todoApp.publisher;

import com.sharan.todoApp.model.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private static final Logger LOGGER= LoggerFactory.getLogger((RabbitMQProducer.class));
    private RabbitTemplate rabbitTemplate;
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(TodoItem todoItem){
        LOGGER.info(String.format("Message sent -> %s",todoItem.getTitle()));
        rabbitTemplate.convertAndSend(exchange,routingKey,todoItem);

    }





}
