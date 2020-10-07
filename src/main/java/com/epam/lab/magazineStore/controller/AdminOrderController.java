package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.Order;
import com.epam.lab.magazineStore.domain.User;
import com.epam.lab.magazineStore.service.MagazineService;
import com.epam.lab.magazineStore.service.OrderService;
import com.epam.lab.magazineStore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

/**
 * Controlling requests from admin order page.
 *
 * @author Daniil_Gorbach on 5/20/2019
 */
public class AdminOrderController extends HttpServlet {
    private static final String LIST_ORDER = "/view/admin/listOrder.jsp";
    private static final String ORDER_ADMIN_PAGE = "/view/admin/order.jsp";
    private final MagazineService magazineService;
    private final UserService userService;
    private final OrderService orderService;

    public AdminOrderController() {
        magazineService = new MagazineService();
        orderService = new OrderService();
        userService = new UserService();
    }

    /**
     * Catches get requests from admin order page.
     *
     * @param request  request from order page
     * @param response response to order page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderToSeeId = request.getParameter("orderToSeeId");
        if (orderToSeeId != null){
            Order order = orderService.getById(Long.parseLong(orderToSeeId));
            User user = userService.findById(order.getUserId());
            Map<Magazine, Integer> magazinesByOrderId = magazineService.getMagazinesByOrderId(order.getId());

            request.setAttribute("order",order);
            request.setAttribute("user",user);
            request.setAttribute("magazinesByOrderId",magazinesByOrderId);
            request.getRequestDispatcher(ORDER_ADMIN_PAGE).forward(request, response);
        } else {
            List<Order> allOrders = orderService.getAll();
            Map<Order, User> orderAndUser = new LinkedHashMap<>();
            for (Order order : allOrders) {
                orderAndUser.put(order, userService.findById(order.getUserId()));
            }
            request.setAttribute("orderAndUser", orderAndUser);
            request.getRequestDispatcher(LIST_ORDER).forward(request, response);
        }

        request.setAttribute("orders", orderService.getAll());
        request.getRequestDispatcher(LIST_ORDER).forward(request, response);
    }
}
