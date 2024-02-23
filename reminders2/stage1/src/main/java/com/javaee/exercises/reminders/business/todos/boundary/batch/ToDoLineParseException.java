package com.javaee.exercises.reminders.business.todos.boundary.batch;

public class ToDoLineParseException extends RuntimeException {
    private final String line;

    public ToDoLineParseException(String message, Throwable cause, String
            line) {
        super(message, cause);
        this.line = line;
    }

    public ToDoLineParseException(String message, String line) {
        super(message);
        this.line = line;
    }

    public String getLine() {
        return line;
    }
}