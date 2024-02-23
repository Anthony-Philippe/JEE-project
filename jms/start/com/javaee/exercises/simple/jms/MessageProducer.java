package com.javaee.exercises.simple.jms;
import jakarta.annotation.Resource;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;

@Stateless
@LocalBean
public class MessageProducer {
    @Inject
    JMSContext context;
    @Resource(lookup = "java:global/queue/simpleQ")
    Queue queue;
    public void sendMessage(String msg) {
        context.createProducer().send(queue, msg);
    }
}