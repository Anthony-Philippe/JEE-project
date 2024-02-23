package com.javaee.exercises.simple.jms;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;

import static Optional.ofNullable;

@WebServlet(urlPatterns = {"/TestServlet"})
public class TestServlet extends HttpServlet implements Serializable {
    private static final long serialVersionUID = 1L;
    @EJB
    private MessageProducer producer;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        String message =
                ofNullable(request.getParameter("message")).orElse("Hello JMS!");
        producer.sendMessage(message);
        try (PrintWriter out = response.getWriter()) {
            out.println("Message sent! " + new Date());
        }
    }
}