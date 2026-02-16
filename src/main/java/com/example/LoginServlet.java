package com.example;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        if ("admin".equals(user) && "Admin@1234".equals(pass)) {
            response.getWriter().println("<h2>Login Successful!</h2>");
        } else {
            response.getWriter().println("<h2>Invalid Credentials</h2>");
        }
    }
}

