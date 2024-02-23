package com.javaee.exercises.reminders.business.todos.boundary;

import com.javaee.exercises.reminders.business.todos.entity.ToDo;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;

public class ToDoResource {
    long id;
    ToDoManager manager;
    public ToDoResource(long id, ToDoManager manager) {
        this.id = id;
        this.manager = manager;
    }
    @GET
    public ToDo find() {
        return manager.findById(id);
    }

    @PUT
    public ToDo save(ToDo todo) {
        todo.setId(id);
        return manager.save(todo);
    }
    @DELETE
    public void delete() {
        manager.delete(id);
    }
}