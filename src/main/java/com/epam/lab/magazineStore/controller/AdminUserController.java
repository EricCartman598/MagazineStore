package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.common.CookieUtil;
import com.epam.lab.magazineStore.domain.Role;
import com.epam.lab.magazineStore.domain.User;
import com.epam.lab.magazineStore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controlling requests from admin users page.
 */
public class AdminUserController extends HttpServlet {
    private static final String LIST_USER = "/view/admin/listUser.jsp";
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);
    private final UserService userService;

    public AdminUserController() {
        userService = new UserService();
    }

    /**
     * Catches get requests from admin users page.
     *
     * @param request  request from order page
     * @param response response to order page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", userService.getAll());
        try {
            request.getRequestDispatcher(LIST_USER).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error("Error during servlet connection", e);
        }
    }

    /**
     * Catches post requests from admin user page.
     *
     * @param request  request from order page
     * @param response response to order page
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Cookie userIdCookie = CookieUtil.getCookieByName(request, "userId");
        if (userIdCookie != null && !"".equals(userIdCookie.getValue())) {
            Long currentUser = Long.parseLong(userIdCookie.getValue());
            Long userId = Long.parseLong(request.getParameter("userId"));
            String role = request.getParameter("role");
            if (userId != 1L && !userId.equals(currentUser)) {
                User user = userService.findById(userId);
                switch (role) {
                    case "Admin": {
                        user.setRole(Role.ADMIN);
                        break;
                    }
                    case "Customer": {
                        user.setRole(Role.CUSTOMER);
                        break;
                    }
                }
                userService.update(user);
            }
        }
        doGet(request, response);
    }
}