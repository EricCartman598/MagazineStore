package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.common.CookieUtil;
import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.domain.User;
import com.epam.lab.magazineStore.service.CartService;
import com.epam.lab.magazineStore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

/**
 * Controlling requests from cart page.
 */
public class CartController extends HttpServlet {
    private static final String CART = "/view/main/cart.jsp";
    private static final Logger LOGGER = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;
    private final UserService userService;

    public CartController() {
        cartService = new CartService();
        userService = new UserService();
    }

    /**
     * Catches get requests from cart page.
     *
     * @param request  request from main page
     * @param response response to main page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            Cookie userIdCookie = CookieUtil.getCookieByName(request, "userId");
            if (userIdCookie != null && !"".equals(userIdCookie.getValue())) {
                Long userId = Long.parseLong(userIdCookie.getValue());
                User userById = userService.findById(userId);
                request.setAttribute("userBalance", userById.getBalance());
            }
            request.getRequestDispatcher(CART).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error("Error during servlet connection", e);
        }
    }

    /**
     * Catches post requests from cart page.
     *
     * @param request  request from main page
     * @param response response to main page
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cookie userIdCookie = CookieUtil.getCookieByName(request, "userId");
        if (userIdCookie != null && !"".equals(userIdCookie.getValue())) {
            Long userId = Long.parseLong(userIdCookie.getValue());
            Map<Magazine, Integer> magazinesCart = (Map<Magazine, Integer>) session.getAttribute("magazinesCart");
            if (!magazinesCart.isEmpty()) {
                cartService.add(magazinesCart, userId);
                cartService.calculateAndSetUserBalance(magazinesCart, userId);
                magazinesCart.clear();
                session.setAttribute("magazinesCart", magazinesCart);
            }
        }
        doGet(request, response);
    }

}
