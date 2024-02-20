package com.javaee.exercises.events;

public class PrintEvent {
    private int pages;

    public PrintEvent(int pages) {
        this.pages = pages;
    }

    public int getPages() {
        return pages;
    }
}

