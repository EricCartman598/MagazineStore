package com.epam.lab.magazineStore.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Util class to get cookie.
 */
public class CookieUtil {

    /**
     * Gets cookie by it's name.
     *
     * @param request        request from some page
     * @param searchedCookie cookie name, we want to find
     * @return cookie by it's name if exist
     */
    public static Cookie getCookieByName(HttpServletRequest request, String searchedCookie) {
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                if (cookieName.equals(searchedCookie)) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
