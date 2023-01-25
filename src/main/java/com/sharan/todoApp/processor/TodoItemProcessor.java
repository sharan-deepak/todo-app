package com.sharan.todoApp.processor;

import com.sharan.todoApp.model.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class TodoItemProcessor implements ItemProcessor<TodoItem,TodoItem> {
    private static final Logger LOGGER= LoggerFactory.getLogger(TodoItemProcessor.class);
    @Override
    public TodoItem process(final TodoItem todoItem) throws Exception{
        final int id = todoItem.getId();
        final String title=todoItem.getTitle();
        final Boolean status=todoItem.getStatus();

        final TodoItem transformedTodoItem= new TodoItem(id,title,status);

        return transformedTodoItem;
    }

}
