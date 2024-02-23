package com.javaee.exercises.reminders.business.todos.boundary;

import com.javaee.exercises.reminders.business.todos.entity.ToDo;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ejb.ConcurrencyManagement;
import jakarta.ejb.ConcurrencyManagementType;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.TransactionPhase;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@Singleton
@Startup
@ServerEndpoint(value = "/changes", encoders = {JsonEncoder.class})
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class ToDoChangeTracker {
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose(Session session) {
        this.session = null;
    }

    public void onToDoChange(@Observes(during =
            TransactionPhase.AFTER_SUCCESS) ToDo todo) {
        if (this.session != null && this.session.isOpen()) {
            try {
                JsonObject event = Json.createObjectBuilder().
                        add("id", todo.getId()).
                        add("caption", todo.getCaption()).
                        add("description", todo.getDescription()).
                        add("done", todo.isDone()).
                        build();
                this.session.getBasicRemote().sendObject(event);
            } catch (Exception ex) {
                Logger.getLogger(ToDoChangeTracker.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        }
    }
}