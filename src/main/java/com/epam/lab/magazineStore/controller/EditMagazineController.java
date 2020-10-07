package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.domain.Magazine;
import com.epam.lab.magazineStore.service.MagazineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Controlling requests from editMagazine page.
 *
 * @author Daniil_Gorbach on 6/5/2019
 */
public class EditMagazineController extends HttpServlet {
    private static final String EDIT_MAGAZINE = "/view/admin/editMagazine.jsp";
    private static final String LIST_MAGAZINE = "/view/admin/listMagazine.jsp";
    private static final Logger LOGGER = LoggerFactory.getLogger(EditMagazineController.class);

    private final MagazineService magazineService;

    public EditMagazineController() {
        magazineService = new MagazineService();
    }

    /**
     * Catches get requests from edit magazine page.
     *
     * @param request  request from edit magazine page
     * @param response response to edit magazine page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Long magazineId = Long.parseLong(request.getParameter("magazineId"));
        request.setAttribute("magazine", magazineService.getMagazineById(magazineId));
        try {
            request.getRequestDispatcher(EDIT_MAGAZINE).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error("Error during servlet connection", e);
        }
    }

    /**
     * Catches post requests from edit magazine page.
     *
     * @param request  request from edit magazine page
     * @param response response to edit magazine page
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String periodDays = request.getParameter("periodDays");
        String price = request.getParameter("price");
        if (name != null && description != null && periodDays != null && price != null) {
            magazineService.updateMagazine(new Magazine(
                    Long.parseLong(id),
                    name,
                    description,
                    Integer.parseInt(periodDays),
                    BigDecimal.valueOf(Double.parseDouble(price))));
        }
        request.setAttribute("magazines", magazineService.getAll());
        try {
            request.getRequestDispatcher(LIST_MAGAZINE).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error("Error during servlet connection", e);
        }
    }
}