package com.dandaev.edu.controllers;

import com.dandaev.edu.dao.MatchDao;
import com.dandaev.edu.entity.MatchEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/matches")
public class MatchesServlet extends HttpServlet {

    private final MatchDao matchDao = new MatchDao();
    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageParam = req.getParameter("page");
        String filterName = req.getParameter("filter_by_player_name");

        int page = 1;
        if (pageParam != null && !pageParam.isBlank()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<MatchEntity> matches;
        long totalMatches;

        if (filterName != null && !filterName.isBlank()) {
            matches = matchDao.findByPlayerName(filterName.trim(), (page - 1) * PAGE_SIZE, PAGE_SIZE);
            totalMatches = matchDao.countByPlayerName(filterName.trim());
        } else {
            matches = matchDao.findAll((page - 1) * PAGE_SIZE, PAGE_SIZE);
            totalMatches = matchDao.countAll();
        }

        int totalPages = (int) Math.ceil((double) totalMatches / PAGE_SIZE);
        if (totalPages == 0) {
            totalPages = 1;
        }

        req.setAttribute("matches", matches);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("filterName", filterName != null ? filterName.trim() : "");

        req.getRequestDispatcher("/WEB-INF/views/matches.jsp").forward(req, resp);
    }
}
