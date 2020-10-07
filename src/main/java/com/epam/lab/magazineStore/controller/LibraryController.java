package com.epam.lab.magazineStore.controller;

import com.epam.lab.magazineStore.service.LibraryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LibraryController extends HttpServlet {
    private static final String LIST_LIBRARY = "/CP/library.jsp";
    private LibraryService libraryService;

    public LibraryController() {
        libraryService = new LibraryService();
    }

    /**
     * Catches get requests from order page.
     *
     * @param request  request from order page
     * @param response response to order page
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("library", libraryService.getAll());
        request.getRequestDispatcher(LIST_LIBRARY).forward(request, response);
    }
}