package com.dandaev.edu.controllers;

import com.dandaev.edu.exceptions.domain.MatchAlreadyFinishedException;
import com.dandaev.edu.exceptions.service.MatchNotFoundException;
import com.dandaev.edu.exceptions.web.ValidationException;
import com.dandaev.edu.model.*;
import com.dandaev.edu.service.FinishedMatchesPersistenceService;
import com.dandaev.edu.service.OngoingMatchesService;
import com.dandaev.edu.validators.Validator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@WebServlet("/match-score")
public class MatchScoreServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchScoreServlet.class);

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getInstance();
    private final FinishedMatchesPersistenceService persistenceService = new FinishedMatchesPersistenceService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String uuid = req.getParameter("uuid");
            Validator.validateUuid(uuid);
            Match match = ongoingMatchesService.getMatch(uuid);
            setMatchAttributes(req, match, uuid);
            req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);
        } catch (ValidationException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/new-match.jsp").forward(req, resp);
        } catch (MatchNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");

        try {
            Validator.validateUuid(uuid);

            Match match = ongoingMatchesService.getMatch(uuid);

            if (match.isOver()) {
                LOGGER.info("Match {} already finished, showing final score", uuid);
                req.setAttribute("final", true);
                req.setAttribute("winnerName", match.getWinner().getName());
                setMatchAttributes(req, match, uuid);
                req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);
                return;
            }

            String winnerParam = req.getParameter("winner");
            Player pointWinner;
            if ("player1".equals(winnerParam)) {
                pointWinner = match.getFirstPlayer();
            } else if ("player2".equals(winnerParam)) {
                pointWinner = match.getSecondPlayer();
            } else {
                throw new ValidationException("Invalid player parameter");
            }

            try {
                match.scorePointTo(pointWinner);
                LOGGER.debug("Point scored by {} in match {}", pointWinner.getName(), uuid);
            } catch (MatchAlreadyFinishedException e) {
                LOGGER.warn("Attempted to score point in already finished match {}", uuid);

                req.setAttribute("final", true);
                req.setAttribute("winnerName", match.getWinner().getName());
                setMatchAttributes(req, match, uuid);
                req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);
                return;
            }

            // Если матч завершился после этого очка
            if (match.isOver()) {
                LOGGER.info("Match {} finished, winner: {}", uuid, match.getWinner().getName());
                persistenceService.persist(match);
                ongoingMatchesService.removeMatch(uuid);
                req.setAttribute("final", true);
                req.setAttribute("winnerName", match.getWinner().getName());
            }

            setMatchAttributes(req, match, uuid);
            req.getRequestDispatcher("/WEB-INF/views/match-score.jsp").forward(req, resp);

        } catch (ValidationException e) {
            LOGGER.warn("Validation error: {}", e.getMessage());
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/new-match.jsp").forward(req, resp);
        } catch (MatchNotFoundException e) {
            LOGGER.warn("Match not found: {}", uuid);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void setMatchAttributes(HttpServletRequest req, Match match, String uuid) {
        req.setAttribute("uuid", uuid);
        req.setAttribute("player1Name", match.getFirstPlayer().getName());
        req.setAttribute("player2Name", match.getSecondPlayer().getName());

        Map<Player, Integer> sets = match.getSetsWinByPlayers();
        req.setAttribute("player1Sets", sets.get(match.getFirstPlayer()));
        req.setAttribute("player2Sets", sets.get(match.getSecondPlayer()));

        GameSet currentSet = match.getCurrentGameSet();
        req.setAttribute("player1Games", currentSet.getGamesWonByPlayer().get(match.getFirstPlayer()));
        req.setAttribute("player2Games", currentSet.getGamesWonByPlayer().get(match.getSecondPlayer()));

        Game currentGame = currentSet.getCurrentGame();
        if (currentGame instanceof RegularGame regularGame) {
            Map<Player, GamePoint> points = regularGame.getPointsWinByPlayer();
            req.setAttribute("player1Points", points.get(match.getFirstPlayer()));
            req.setAttribute("player2Points", points.get(match.getSecondPlayer()));
        } else if (currentGame instanceof TieBreakGame tieBreak) {
            req.setAttribute("player1Points", tieBreak.getFirstPlayerPoints());
            req.setAttribute("player2Points", tieBreak.getSecondPlayerPoints());
        }
    }
}
