package com.javaee.exercices.servlets;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletRequestAttributeEvent;
import jakarta.servlet.ServletRequestAttributeListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SimpleServletListener implements ServletContextListener,
        ServletRequestAttributeListener {
    static final Logger LOG
            = Logger.getLogger("mood.web.SimpleServletListener");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.info("Context initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.info("Context destroyed");
    }

    @Override
    public void attributeAdded(ServletRequestAttributeEvent event) {
        LOG.log(Level.INFO, "Attribute {0} has been added, with value: {1}",
                new Object[]{event.getName(), event.getValue()});
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent event) {
        LOG.log(Level.INFO, "Attribute {0} has been removed",
                event.getName());
    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent event) {
        LOG.log(Level.INFO, "Attribute {0} has been replaced, with value: {1}",
                new Object[]{event.getName(), event.getValue()});
    }
}