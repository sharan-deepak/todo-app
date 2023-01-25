package com.sharan.todoApp.consumer;

import com.sharan.todoApp.model.TodoItem;
import com.sharan.todoApp.publisher.RabbitMQProducer;
import com.sharan.todoApp.repository.TodoRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {
    private static final Logger LOGGER= LoggerFactory.getLogger(RabbitMQProducer.class);
    @Autowired
    private TodoRepo todoRepo;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(TodoItem todoItem){
        LOGGER.info(String.format("\nReceived todo item \nid:%d\nmessage:%s\nstatus:%b",todoItem.getId(),todoItem.getTitle(),todoItem.getStatus()));
        todoRepo.save(todoItem);
    }
}
