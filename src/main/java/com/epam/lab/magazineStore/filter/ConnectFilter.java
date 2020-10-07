package com.epam.lab.magazineStore.filter;

import com.epam.lab.magazineStore.common.CookieUtil;
import com.epam.lab.magazineStore.domain.Magazine;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Prepare data for working with cart
 */
public class ConnectFilter extends HttpFilter {

    /**
     * Filter, that creates cart if it is not yet exists in session.
     * <p>
     * Checks if {@code magazinesIdInCart} is represented and updating cart.
     *
     * @param request  request from some page
     * @param response response to some page
     * @param chain    filter chain
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.isNew()) {
            Map<Magazine, Integer> magazinesCart = new HashMap<>();
            Cookie lang = CookieUtil.getCookieByName(request,"lang");
            if (lang == null){
                Cookie cookie = new Cookie("lang", "en");
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            session.setAttribute("magazinesCart", magazinesCart);
        }
        chain.doFilter(request, response);
    }
}
