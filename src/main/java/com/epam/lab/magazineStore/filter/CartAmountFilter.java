package com.epam.lab.magazineStore.filter;

import com.epam.lab.magazineStore.common.CookieUtil;
import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.service.MagazineService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Filter to save new amounts of items in cart.
 */
public class CartAmountFilter extends HttpFilter {
    private MagazineService magazineService = new MagazineService();

    /**
     * Convert data from cookie after leaving cart page into cart representation.
     * <p>
     * Parse cookie pairs to new map and returning it to session
     *
     * @param request  request from some page
     * @param response response to some page
     * @param chain    filter chain
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (!session.isNew()) {
            Cookie magazinesWithAmount = CookieUtil.getCookieByName(request, "magazinesWithAmount");
            updateCartAfterCartCookie(session, magazinesWithAmount, response);
        }
        chain.doFilter(request, response);
    }

    private void updateCartAfterCartCookie(HttpSession session, Cookie cookie, HttpServletResponse response) {
        if (cookie != null && !"".equals(cookie.getValue())) {
            Map<Magazine, Integer> magazinesCart = new HashMap<>();
            if (!"-1".equals(cookie.getValue())) {
                List<String> magazinesAndAmounts = Arrays.asList(cookie.getValue().split("!"));
                for (String magazineWithAmount : magazinesAndAmounts) {
                    String[] magazineAndAmount = magazineWithAmount.split("-");
                    magazinesCart.put(magazineService.getMagazineById(Long.parseLong(magazineAndAmount[0])), Integer.parseInt(magazineAndAmount[1]));
                }
            }
            Cookie newCookie = new Cookie("magazinesWithAmount", "");
            newCookie.setPath("/");
            response.addCookie(newCookie);
            session.setAttribute("magazinesCart", magazinesCart);
        }
    }
}
