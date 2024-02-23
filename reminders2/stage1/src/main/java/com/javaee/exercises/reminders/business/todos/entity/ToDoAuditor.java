package com.javaee.exercises.reminders.business.todos.entity;

import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;

public class ToDoAuditor {
    @Inject
    Event<ToDo> events;
    @PostPersist
    @PostUpdate
    public void onToDoPersist(ToDo todo) {
        this.events.fire(todo);
    }
}