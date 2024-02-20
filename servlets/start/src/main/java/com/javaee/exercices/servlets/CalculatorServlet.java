package com.javaee.exercices.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Calculator")
public class CalculatorServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String num1Str = request.getParameter("num1");
            String num2Str = request.getParameter("num2");

            if (num1Str != null && num2Str != null) {
                try {
                    int num1 = Integer.parseInt(num1Str);
                    int num2 = Integer.parseInt(num2Str);
                    int sum = num1 + num2;
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Calculator Servlet</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Sum Calculation</h1>");
                    out.println("<p>The sum of " + num1 + " and " + num2 + " is " + sum + ".</p>");
                    out.println("</body>");
                    out.println("</html>");
                } catch (NumberFormatException e) {
                    out.println("Invalid input. Please enter valid numbers.");
                }
            } else {
                out.println("Please enter two numbers.");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "The Calculator says hello.";
    }
}