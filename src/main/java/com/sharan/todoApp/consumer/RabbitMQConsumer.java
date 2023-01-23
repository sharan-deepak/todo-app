package com.sharan.todoApp.consumer;

import com.sharan.todoApp.model.TodoItem;
import com.sharan.todoApp.publisher.RabbitMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQProducer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(TodoItem todoItem){
        LOGGER.info(String.format("\nReceived todo item \nid:%d\nmessage:%s\nstatus:%b",todoItem.getId(),todoItem.getTitle(),todoItem.getStatus()));
    }
}
