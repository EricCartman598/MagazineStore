package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.service.MagazineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * This class represents controller for admin magazine page.
 *
 * @author Aleksandr_Samsonov
 */
public class AdminMagazineController extends HttpServlet {
    private static final String LIST_MAGAZINE = "/view/admin/listMagazine.jsp";
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminMagazineController.class);

    private final MagazineService magazineService;

    public AdminMagazineController() {
        magazineService = new MagazineService();
    }

    /**
     * Catches get requests from admin magazine page.
     *
     * @param request  request from magazine page
     * @param response response to magazine page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("magazines", magazineService.getAll());
        try {
            request.getRequestDispatcher(LIST_MAGAZINE).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error("Error during servlet connection", e);
        }
    }

    /**
     * Catches post requests from admin magazine page.
     *
     * @param request  request from magazine page
     * @param response response to magazine page
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if ("addMagazine".equals(action)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String periodDays = request.getParameter("periodDays");
            String price = request.getParameter("price");
            if (name != null && description != null && periodDays != null && price != null) {
                magazineService.add(new Magazine(
                        name,
                        description,
                        Integer.parseInt(periodDays),
                        BigDecimal.valueOf(Double.parseDouble(price))));
            }
        }
        request.setAttribute("magazines", magazineService.getAll());
        doGet(request, response);
    }
}