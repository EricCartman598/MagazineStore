package com.epam.lab.magazineStore.filter;

import com.epam.lab.magazineStore.domain.Role;
import com.epam.lab.magazineStore.domain.User;
import com.epam.lab.magazineStore.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserFilter extends HttpFilter {
    private UserService userService = new UserService();

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        Map<String, String> cookiesMap = new HashMap<>();
        for (Cookie cookie : cookies) {
            cookiesMap.put(cookie.getName(), cookie.getValue());
        }

        User user = null;
        if (cookiesMap.get("userId") != null && !cookiesMap.get("userId").isEmpty()) {
            user = userService.findById(Long.valueOf(cookiesMap.get("userId")));
        }

        boolean isRegistered = false;
        if (cookiesMap.get("userHashPass") != null && !cookiesMap.get("userHashPass").isEmpty()) {
            isRegistered = userService.isPasswordCorrect(user, Integer.valueOf(cookiesMap.get("userHashPass")));
            if (isRegistered) {
                req.setAttribute("user", user);
                req.setAttribute("isAdmin", user.getRole());
            } else {
                req.setAttribute("user", null);
            }
        }

        req.setAttribute("isRegistered", isRegistered);
        chain.doFilter(req, res);
    }
}
