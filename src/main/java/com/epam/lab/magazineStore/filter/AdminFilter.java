package com.epam.lab.magazineStore.filter;

import com.epam.lab.magazineStore.common.CookieUtil;
import com.epam.lab.magazineStore.domain.Role;
import com.epam.lab.magazineStore.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to check possibility to enter admin section.
 */
public class AdminFilter extends HttpFilter {
    private UserService userService = new UserService();

    /**
     * Checks if user has {@code Admin} role.
     * <p>
     * Else redirect to man page
     *
     * @param request  request from some page
     * @param response response to some page
     * @param chain    filter chain
     */
    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Cookie userIdCookie = CookieUtil.getCookieByName(request, "userId");
        if (userIdCookie != null && !"".equals(userIdCookie.getValue())) {
            Long userId = Long.parseLong(userIdCookie.getValue());
            Role role = userService.findById(userId).getRole();
            if (!role.equals(Role.ADMIN)) {
                response.sendRedirect("/main/magazines");
                return;
            }
        } else {
            response.sendRedirect("/main/magazines");
            return;
        }
        chain.doFilter(request, response);
    }
}
