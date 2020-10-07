package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.common.Contract;
import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.Order;
import com.epam.lab.magazineStore.domain.User;
import com.epam.lab.magazineStore.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class UserController extends HttpServlet {
    private UserService userService = new UserService();
    private User user;

    private void resetUserCookies(HttpServletResponse response) {
        response.addCookie(new Cookie("userId", ""));
        response.addCookie(new Cookie("userEmail", ""));
        response.addCookie(new Cookie("userHashPass", ""));
        response.addCookie(new Cookie("userFirstName", ""));
        response.addCookie(new Cookie("userLastName", ""));
        response.addCookie(new Cookie("userBalance", ""));
        response.addCookie(new Cookie("userRole", ""));
    }

    private void setUserCookies(HttpServletResponse response, User user) {
        response.addCookie(new Cookie("userId", user.getId().toString()));
        response.addCookie(new Cookie("userEmail", user.getEmail()));
        response.addCookie(new Cookie("userHashPass", user.getPassword().toString()));
        response.addCookie(new Cookie("userFirstName", user.getFirstName()));
        response.addCookie(new Cookie("userLastName", user.getLastName()));
        response.addCookie(new Cookie("userBalance", user.getBalance().toString()));
        response.addCookie(new Cookie("userRole", user.getRole().toString()));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        user = (User) request.getAttribute("user");
        String logout = request.getParameter("logout");
        String showData = request.getParameter("showData");
        request.setAttribute("showData", showData);

        if (logout != null && logout.equals("true")) {
            resetUserCookies(response);
            response.setHeader("Refresh", "0; " + "/index.jsp");
        } else if (showData == null || showData.equals("userInfo")) {
            request.getRequestDispatcher("/view/main/userProfile.jsp").forward(request, response);
        } else if (showData.equals("userLibrary")) {
            Map<Magazine, Timestamp> userLibrary = userService.getUserLibrary(user.getId());
            request.setAttribute("userLibrary", userLibrary);
            request.getRequestDispatcher("/view/main/userProfile.jsp").forward(request, response);
        } else if (showData.equals("userHistory")) {
            Map<Order, List<Magazine>> userHistory = userService.getUserHistory(user.getId());
            request.setAttribute("userHistory", userHistory);
            request.getRequestDispatcher("/view/main/userProfile.jsp").forward(request, response);
        } else {
            response.setHeader("Refresh", "0; " + "/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case "/changeUserData":
                user = (User) req.getAttribute("user");

                String newFirstName = req.getParameter("firstName");
                String newLastName = req.getParameter("lastName");
                String newBirthDate = req.getParameter("birthDate");
                String newLogin = req.getParameter("login");

                user.setFirstName(newFirstName);
                user.setLastName(newLastName);
                user.setBirthDate(userService.getBirthDateTimeStamp(newBirthDate));
                user.setEmail(newLogin);
                userService.update(user);
                resp.setHeader("Refresh", "0; " + "/index.jsp");
                break;
            case "/changeUserPassword":
                user = (User) req.getAttribute("user");
                String oldPassword = req.getParameter("oldPassword");
                String newPassword = req.getParameter("newPassword");
                try {
                    userService.changeUserPassword(user, oldPassword, newPassword);
                    req.setAttribute("message", Contract.PASSWORD_HAS_BEEN_CHANGED_MSG);
                    resp.setHeader("Refresh", "0; " + "/index.jsp");
                } catch (RuntimeException e) {
                    req.setAttribute("message", e.getMessage());
                    req.getRequestDispatcher("/view/main/userProfile.jsp").forward(req, resp);
                }
                break;
            case "/topUpUserBalance":
                user = (User) req.getAttribute("user");
                String amount = req.getParameter("amount");
                userService.topUpUserBalance(user, amount);
                resp.setHeader("Refresh", "0; " + "/view/main/userProfile.jsp");
                break;
            case "/signIn":
                signIn(req, resp);
                break;
            default:
                resp.setHeader("Refresh", "0; " + "/index.jsp");
                break;
        }
    }

    private void signIn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = userService.getUserByEmail(req.getParameter("login"));
        try {
            userService.checkUserPermission(user, req.getParameter("password"));
            req.setAttribute("error", "");
            setUserCookies(resp, user);
            resp.setHeader("Refresh", "0; " + "/index.jsp");
        } catch (RuntimeException e) {
            req.setAttribute("error", e.getMessage());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/view/main/signIn.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}