package com.sharan.todoApp.controller;


import com.sharan.todoApp.model.TodoItem;
import com.sharan.todoApp.publisher.RabbitMQProducer;
import com.sharan.todoApp.repository.TodoRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/todo")
public class TodoController {

    private RabbitMQProducer producer;
    public TodoController(RabbitMQProducer producer) {
        this.producer = producer;
    }
    @Autowired
    private TodoRepo todoRepo;

    @GetMapping()
    public List<TodoItem> findAll(){
        return todoRepo.findAll();
    }

    @PostMapping()
    public String save(@Valid @NotNull @RequestBody TodoItem todoItem){
        todoRepo.save(todoItem);
        producer.sendMessage(todoItem);
        return "Succesfully Sent to RabbitMQ";
    }

    @PutMapping
    public TodoItem update(@Valid @NotNull @RequestBody TodoItem todoItem){
        return todoRepo.save(todoItem);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable long id){
        todoRepo.deleteById(id);
    }


}
