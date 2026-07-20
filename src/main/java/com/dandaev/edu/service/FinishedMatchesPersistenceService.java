package com.dandaev.edu.service;

import com.dandaev.edu.dao.MatchDao;
import com.dandaev.edu.dao.PlayerDao;
import com.dandaev.edu.entity.MatchEntity;
import com.dandaev.edu.entity.PlayerEntity;
import com.dandaev.edu.exceptions.domain.MatchNotFinishedException;
import com.dandaev.edu.model.Match;
import com.dandaev.edu.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class FinishedMatchesPersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FinishedMatchesPersistenceService.class);
    private final PlayerDao playerDao = new PlayerDao();
    private final MatchDao matchDao = new MatchDao();

    public void persist(Match match) {
        if (!match.isOver()) {
            LOGGER.warn("Attempt to persist an unfinished match");
            throw new MatchNotFinishedException("Cannot save a match that is not finished");
        }

        Player winnerDomain = match.getWinner();
        Player player1Domain = match.getFirstPlayer();
        Player player2Domain = match.getSecondPlayer();

        PlayerEntity player1Entity = playerDao.findOrCreate(player1Domain);
        PlayerEntity player2Entity = playerDao.findOrCreate(player2Domain);
        PlayerEntity winnerEntity = playerDao.findOrCreate(winnerDomain);

        MatchEntity matchEntity = new MatchEntity(player1Entity, player2Entity, winnerEntity, LocalDateTime.now());
        matchDao.save(matchEntity);

        LOGGER.info("Persisted completed match: {} vs {}, winner: {}", player1Domain.getName(), player2Domain.getName(), winnerDomain.getName());
    }


    private PlayerEntity getOrCreatePlayerEntity(Player player) {
        return playerDao.findOrCreate(player);
    }

    private Player getFirstPlayerFromMatch(Match match) {
        return match.getFirstPlayer();
    }

    private Player getSecondPlayerFromMatch(Match match) {
        return match.getSecondPlayer();
    }
}