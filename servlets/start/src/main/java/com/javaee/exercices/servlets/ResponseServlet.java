package com.javaee.exercices.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Response")
public class ResponseServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username");
            if (username != null && !username.isEmpty()) {
                out.println("<h2>Hello, " + username + "!</h2>");
            }
        }
    }
    @Override
    public String getServletInfo() {
        return "The Response servlet says hello.";
    }
}