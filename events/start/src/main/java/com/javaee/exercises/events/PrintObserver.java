package com.javaee.exercises.events;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Default;

@Singleton
@Startup
public class PrintObserver {

    public void onPrint(@Observes @Default PrintEvent event) {
        System.out.println("Printing " + event.getPages() + " pages");
    }

    public void onBind(@Observes @BindIt PrintEvent event) {
        System.out.println("Printing and binding " + event.getPages() + " pages");
    }
}