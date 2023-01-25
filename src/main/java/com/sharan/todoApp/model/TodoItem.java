package com.sharan.todoApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;

@Entity
@Table(name = "todo")
public class TodoItem implements Serializable {
    private int id;
    @NotBlank
    private String title;
    private boolean status;

    public TodoItem(int id, String title, boolean status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    public TodoItem() {
    }
    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
