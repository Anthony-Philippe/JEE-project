package com.javaee.exercises.reminders.business.todos.boundary.batch;

import com.javaee.exercises.reminders.business.todos.entity.ToDo;
import jakarta.batch.api.chunk.ItemProcessor;
import jakarta.batch.runtime.context.StepContext;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Dependent
@Named("ToDoItemProcessor")
public class ToDoItemProcessor implements ItemProcessor {

    @Inject
    private StepContext stepContext;

    private static final String FORBIDDEN_WORD = "forbiddenWordInCaption";

    @Override
    public Object processItem(Object item) throws Exception {
        ToDo todo = (ToDo) item;
        String forbiddenWordInCaption = (String) stepContext.getTransientUserData();

        if (todo.getCaption().contains(forbiddenWordInCaption)) {
            return null;
        }
        return todo;
    }
}