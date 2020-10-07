package com.epam.lab.magazineStore.filter;

import com.epam.lab.magazineStore.common.CookieUtil;
import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.service.MagazineService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Filter to add new items to cart.
 */
public class AddToCartFilter extends HttpFilter {
    private MagazineService magazineService = new MagazineService();

    /**
     * Convert data from cookie after magazine page into cart representation.
     * <p>
     * Process divided into four parts:
     * <p>
     * 1 - clearing cart if the income value is "-1"
     * <p>
     * 2 - adding new magazines from cookie to cart, with the help of cookie, where values separated by "!" sign
     * <p>
     * 3 - removing not yet useful magazines from cart
     * <p>
     * 4 - returning cart back to session
     *
     * @param request  request from some page
     * @param response response to some page
     * @param chain    filter chain
     */
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (!session.isNew()) {
            Cookie magazinesIdInCart = CookieUtil.getCookieByName(request, "magazinesIdInCart");
            updateCartAfterMagazineCookie(session, magazinesIdInCart, response);
        }
        chain.doFilter(request, response);
    }


    private void updateCartAfterMagazineCookie(HttpSession session, Cookie cookie, HttpServletResponse response) {
        if (cookie != null && !"".equals(cookie.getValue())) {
            Map<Magazine, Integer> magazinesCart = (Map<Magazine, Integer>) session.getAttribute("magazinesCart");
            if ("-1".equals(cookie.getValue())) {
                magazinesCart.clear();
            } else {
                List<String> magazinesId = Arrays.asList(cookie.getValue().split("!"));
                for (String magazineId : magazinesId) {
                    if (!magazinesCart.containsKey(magazineService.getMagazineById(Long.parseLong(magazineId)))) {
                        magazinesCart.put(magazineService.getMagazineById(Long.parseLong(magazineId)), 1);
                    }
                }
                Iterator<Magazine> iter = magazinesCart.keySet().iterator();
                while (iter.hasNext()) {
                    if (!magazinesId.contains(iter.next().getId().toString())) {
                        iter.remove();
                    }
                }
            }
            Cookie newCookie = new Cookie("magazinesIdInCart", "");
            newCookie.setPath("/");
            response.addCookie(newCookie);
            session.setAttribute("magazinesCart", magazinesCart);
        }
    }
}
