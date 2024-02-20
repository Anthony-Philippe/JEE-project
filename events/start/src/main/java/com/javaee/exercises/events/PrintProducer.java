package com.javaee.exercises.events;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Stateless
@LocalBean
public class PrintProducer {
    @Inject Event<PrintEvent> printEvent;
    @Inject @BindIt Event<PrintEvent> bindEvent;

    public void print(int pages) {
        PrintEvent event = new PrintEvent(pages);
        if (pages >= 10) {
            bindEvent.fire(event);
        }
        else {
            printEvent.fire(event);
        }
    }
}