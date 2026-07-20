package com.dandaev.edu.controllers;

import com.dandaev.edu.exceptions.web.ValidationException;
import com.dandaev.edu.model.Player;
import com.dandaev.edu.service.OngoingMatchesService;
import com.dandaev.edu.validators.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player1Name = req.getParameter("player1");
        String player2Name = req.getParameter("player2");

        try {
            Validator.validatePlayerNames(player1Name, player2Name);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/new-match.jsp").forward(req, resp);
            return;
        }

        Player player1 = new Player(player1Name.trim());
        Player player2 = new Player(player2Name.trim());

        String uuid = ongoingMatchesService.createMatch(player1, player2);
        resp.sendRedirect(req.getContextPath() + "/match-score?uuid=" + uuid);
    }
}
