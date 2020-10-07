package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.service.MagazineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controlling requests from main page with magazines
 *
 * @author Daniil_Gorbach on 5/21/2019
 */
public class MainMagazineController extends HttpServlet {
    private static final String MAGAZINE_LIST = "/view/main/magazinePage.jsp";
    private static final Logger LOGGER = LoggerFactory.getLogger(MainMagazineController.class);
    private final MagazineService magazineService;

    public MainMagazineController() {
        magazineService = new MagazineService();
    }

    /**
     * Catches get requests from main page.
     *
     * @param request  request from main page
     * @param response response to main page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("magazines", magazineService.getAll());
        try {
            request.getRequestDispatcher(MAGAZINE_LIST).forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.error("Error during servlet connection", e);
        }
    }
}
