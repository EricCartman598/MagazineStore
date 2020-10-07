package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.Order;
import com.epam.lab.magazineStore.domain.User;
import com.epam.lab.magazineStore.service.UserService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    UserService userService = mock(UserService.class);

    @Test
    public void doGet_logout_redirectToMainPage() {
        UserController userController = new UserController();

        when(request.getParameter("logout")).thenReturn("true");
        try {
            userController.doGet(request, response);
        } catch (Exception e) {
            logger.error("error while execute doGet method");
        }

        verify(request, times(1)).
                setAttribute(eq("showData"), any());
        verify(response, times(1)).
                setHeader(eq("Refresh"), eq("0; " + "/index.jsp"));
    }

    @Test
    public void doGet_showUserInfo_redirectToProfilePage() {
        UserController userController = new UserController();
        String showData = "userInfo";

        when(request.getParameter("logout")).thenReturn("");
        when(request.getParameter("showData")).thenReturn(showData);
        when(request.getRequestDispatcher(any())).thenReturn(dispatcher);

        try {
            userController.doGet(request, response);

            verify(request, times(1)).
                    setAttribute(eq("showData"), eq(showData));
            verify(request, times(1)).
                    getRequestDispatcher(eq("/view/main/userProfile.jsp"));
            verify(dispatcher, times(1)).
                    forward(request, response);
        } catch (Exception e) {
            logger.error("error while execute doGet method");
        }
    }

    @Test
    public void doGet_showUserHistory_redirectToProfilePage() {
        UserController userController = new UserController();
        String showData = "userHistory";
        Map<Order, List<Magazine>> userHistory = new LinkedHashMap<>();

        when(request.getParameter("logout")).thenReturn("");
        when(request.getParameter("showData")).thenReturn(showData);
        when(request.getRequestDispatcher(any())).thenReturn(dispatcher);
        when(userService.getUserHistory(any())).thenReturn(userHistory);

        try {
            userController.doGet(request, response);

            verify(request, times(1)).
                    setAttribute(eq("showData"), eq(showData));
            verify(request, times(1)).
                    getRequestDispatcher(eq("/view/main/userProfile.jsp"));
            verify(dispatcher, times(1)).
                    forward(request, response);
            verify(userService, times(1)).getUserHistory(any());
            request.setAttribute("userHistory", userHistory);
        } catch (Exception e) {
            logger.error("error while execute doGet method");
        }
    }

    @Test
    public void doGet_showUserLibrary_redirectToProfilePage() {
        UserController userController = new UserController();
        String showData = "userLibrary";
        Map<Magazine, Timestamp> userLibrary = new LinkedHashMap<>();

        when(request.getParameter("logout")).thenReturn("");
        when(request.getParameter("showData")).thenReturn(showData);
        when(request.getRequestDispatcher(any())).thenReturn(dispatcher);
        when(userService.getUserLibrary(any())).thenReturn(userLibrary);

        try {
            userController.doGet(request, response);

            verify(request, times(1)).
                    setAttribute(eq("showData"), eq(showData));
            verify(request, times(1)).
                    getRequestDispatcher(eq("/view/main/userProfile.jsp"));
            verify(dispatcher, times(1)).
                    forward(request, response);
            verify(userService, times(1)).getUserHistory(any());
            request.setAttribute("userHistory", userLibrary);
        } catch (Exception e) {
            logger.error("error while execute doGet method");
        }
    }

    @Test
    public void doPost_changeUserData_redirectToMainPage() {
        UserController userController = new UserController();
        String servletPath = "/changeUserData";
        User user = new User();

        when(request.getServletPath()).thenReturn(servletPath);
        when(request.getAttribute("user")).thenReturn(user);

        try {
            userController.doPost(request, response);

            verify(request, times(1)).getServletPath();
            verify(request, times(1)).getAttribute("user");
            verify(request, times(4)).getParameter(any());
            verify(userService, times(1)).update(user);
            verify(response, times(1)).
                    setHeader(eq("Refresh"), eq("0; " + "/index.jsp"));
        } catch (Exception e) {
            logger.error("error while execute doPost method");
        }
    }

    @Test
    public void doPost_topUpBalance_redirectToProfilePage() {
        UserController userController = new UserController();
        String servletPath = "/topUpUserBalance";
        String amount = "123";
        User user = new User();

        when(request.getServletPath()).thenReturn(servletPath);
        when(request.getParameter("amount")).thenReturn(amount);

        try {
            userController.doPost(request, response);

            verify(request, times(1)).getAttribute("user");
            verify(request, times(1)).getParameter("amount");
            verify(userService, times(1)).
                    topUpUserBalance(user, amount);
            verify(response, times(1)).
                    setHeader(eq("Refresh"), eq("/view/main/userProfile.jsp"));

        } catch (Exception e) {
            logger.error("error while execute doPost method");
        }
    }

    @Test
    public void doPost_withoutParams_redirectToMainPage() {
        UserController userController = new UserController();
        String servletPath = "/";

        when(request.getServletPath()).thenReturn(servletPath);

        try {
            userController.doPost(request, response);

            verify(response, times(1)).
                    setHeader(eq("Refresh"), eq("0; " + "/index.jsp"));
        } catch (Exception e) {
            logger.error("error while execute doPost method");
        }
    }

    @Test
    public void doPost_changeUserPasswordWithIncorrectOldPassword_redirectToMainPage() {
        UserController userController = new UserController();
        String servletPath = "/changeUserPassword";

        when(request.getServletPath()).thenReturn(servletPath);
        when(request.getRequestDispatcher(any())).thenReturn(dispatcher);
        doThrow(new RuntimeException()).
                when(userService).
                changeUserPassword(any(), any(), any());

        try {
            userController.doPost(request, response);

            verify(request, times(1)).
                    getRequestDispatcher(eq("/view/main/userProfile.jsp"));
            verify(dispatcher, times(1)).
                    forward(request, response);
        } catch (Exception e) {
            logger.error("error while execute doPost method");
        }
    }
}

