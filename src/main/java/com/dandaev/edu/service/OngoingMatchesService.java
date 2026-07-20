package com.dandaev.edu.service;

import com.dandaev.edu.exceptions.domain.InvalidPlayerException;
import com.dandaev.edu.exceptions.service.MatchNotFoundException;
import com.dandaev.edu.model.Match;
import com.dandaev.edu.model.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();

    private final ConcurrentHashMap<String, Match> currentMatches = new ConcurrentHashMap<>();

    private OngoingMatchesService() {
    }

    public static OngoingMatchesService getInstance() {
        return INSTANCE;
    }

    public String createMatch(Player player1, Player player2) {
        if (player1 == null || player2 == null) {
            throw new InvalidPlayerException("Players must not be null");
        }
        if (player1.equals(player2)) {
            throw new InvalidPlayerException("A player cannot play against themselves");
        }

        String uuid = UUID.randomUUID().toString();
        Match match = new Match(player1, player2);
        currentMatches.put(uuid, match);

        return uuid;
    }

    public Match getMatch(String uuid) {
        Match match = currentMatches.get(uuid);
        if (match == null) {
            throw new MatchNotFoundException(uuid);
        }
        return match;
    }

    public void removeMatch(String uuid) {
        currentMatches.remove(uuid);
    }
}