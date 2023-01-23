package com.sharan.todoApp.repository;

import com.sharan.todoApp.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository<TodoItem,Long> {
}
