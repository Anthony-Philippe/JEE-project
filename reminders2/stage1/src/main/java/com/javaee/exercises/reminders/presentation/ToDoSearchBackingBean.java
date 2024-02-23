package com.javaee.exercises.reminders.presentation;

import com.javaee.exercises.reminders.business.Contexts;
import com.javaee.exercises.reminders.business.todos.boundary.ToDoManager;
import com.javaee.exercises.reminders.business.todos.entity.ToDo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

@Model
public class ToDoSearchBackingBean {
    
    @Inject
    ToDoManager boundary;
    
    private String text;

    @Contexts
    private List<String> contexts = new ArrayList<>();
    
    private List<ToDo> results = null;
    
    public boolean matches(final ToDo todo) {
        if (text != null && !todo.getCaption().toLowerCase().contains(text.toLowerCase())) {
            return false;
        }

        if (!contexts.isEmpty() && todo.getContexts().stream().noneMatch(contexts::contains)) {
            return false;
        }

        return true;
    }
    
    public void search() {
        this.results = this.boundary.all().parallelStream().filter(this::matches).collect(Collectors.toList());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getContexts() {
        return contexts;
    }    

    public void setContexts(List<String> contexts) {
        this.contexts = contexts;
    }
    
    public List<ToDo> getResults() {
        return results;
    }    
        
}
