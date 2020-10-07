package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.domain.User;
import com.epam.lab.magazineStore.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class RegistrationController extends HttpServlet {
    private final UserService userService;

    public RegistrationController() {
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher;
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String birthDate = req.getParameter("birthDate");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String passwordAgain = req.getParameter("passwordAgain");

        try {
            userService.validateFields(firstName, lastName, birthDate, login, password, passwordAgain);
            req.setAttribute("error", "");
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            requestDispatcher = req.getRequestDispatcher("/view/main/register.jsp");
            requestDispatcher.forward(req, resp);
            return;
        }

        Timestamp birthDateTimeStamp = userService.getBirthDateTimeStamp(birthDate);
        Integer passwordHash = userService.getPasswordHash(password);
        User newUser = new User(firstName, lastName, login, birthDateTimeStamp, passwordHash);
        userService.add(newUser);
        // TODO protection from XSS

        requestDispatcher = req.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(req, resp);
    }
}

