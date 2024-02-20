package com.javaee.exercises.reminders.presentation;

import com.javaee.exercises.reminders.business.todos.boundary.ToDoManager;
import com.javaee.exercises.reminders.business.todos.entity.ToDo;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;

@Model
public class ToDoBackingBean {
    @Inject
    ToDoManager boundary;
    ToDo todo;

    @PostConstruct
    public void init() {
        this.todo = new ToDo();
    }

    public ToDo getTodo() {
        return todo;
    }

    public List<ToDo> getToDos() {
        return this.boundary.all();
    }

    public void addToDo() {
        ToDo td = this.boundary.save(todo);
        FacesMessage message = new FacesMessage(String.format("ToDo \"%1s\" created!", td.getCaption()));
        FacesContext.getCurrentInstance().addMessage("", message);
        init();
    }

    public void changeStatus(ToDo toDo) {
        toDo.setDone(!toDo.isDone());
        this.boundary.save(toDo);
    }

    public String editToDo(ToDo toDo) {
        this.todo = toDo;
        return "edit-todo";
    }
    public String saveToDo() {
        this.boundary.save(todo);
        FacesMessage message = new FacesMessage(String.format("ToDo \"%s\" (#%d) saved!",
        todo.getCaption(), todo.getId()));
        FacesContext.getCurrentInstance().addMessage("", message);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        return "list-todos?faces-redirect=true";
    }

    public void removeToDo(ToDo toDo) {
        this.boundary.delete(toDo.getId());
    }
}