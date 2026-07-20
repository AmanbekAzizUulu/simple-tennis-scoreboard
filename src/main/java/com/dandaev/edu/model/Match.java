package com.dandaev.edu.model;

import com.dandaev.edu.exceptions.domain.InvalidPlayerException;
import com.dandaev.edu.exceptions.domain.MatchAlreadyFinishedException;

import java.util.HashMap;
import java.util.Map;

public class Match implements Game {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private Player winner;

    private final Map<Player, Integer> setsWinByPlayers;

    private boolean isOver;

    private GameSet currentGameSet;


    public Match(Player firstPlayer, Player secondPlayer) {
        if (firstPlayer == null || secondPlayer == null) {
            throw new InvalidPlayerException("Player can't be null");
        }
        if (firstPlayer.equals(secondPlayer)) {
            throw new InvalidPlayerException("Players must be different");
        }

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.setsWinByPlayers = new HashMap<>();

        this.isOver = false;

        this.currentGameSet = new GameSet(firstPlayer, secondPlayer);

        this.setsWinByPlayers.put(firstPlayer, Integer.valueOf(0));
        this.setsWinByPlayers.put(secondPlayer, Integer.valueOf(0));
    }


    @Override
    public void scorePointTo(Player player) {
        if (isOver) {
            throw new MatchAlreadyFinishedException();
        }
        if (!player.equals(firstPlayer) && !player.equals(secondPlayer)) {
            throw new InvalidPlayerException("Unknown player");
        }

        currentGameSet.scorePointTo(player);

        if (currentGameSet.isOver()) {
            Player gameSetWinner = currentGameSet.getWinner();
            setsWinByPlayers.put(gameSetWinner, setsWinByPlayers.get(gameSetWinner) + 1);
            if (!checkMatchComplete()) {
                currentGameSet = new GameSet(firstPlayer, secondPlayer);
            }
        }
    }

    private boolean checkMatchComplete() {
        if (setsWinByPlayers.get(firstPlayer) == 2) {
            winner = firstPlayer;
            return isOver = true;
        }
        if (setsWinByPlayers.get(secondPlayer) == 2) {
            winner = secondPlayer;
            return isOver = true;
        }
        return false;
    }

    @Override
    public boolean isOver() {
        return isOver;
    }

    @Override
    public Player getWinner() {
        return winner;
    }


    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Map<Player, Integer> getSetsWinByPlayers() {
        return setsWinByPlayers;
    }

    public int getSetsWonBy(Player player) {
        if (player == null) {
            throw new InvalidPlayerException("Player cannot be null");
        }
        Integer sets = setsWinByPlayers.get(player);
        if (sets == null) {
            throw new InvalidPlayerException("Unknown player");
        }
        return sets;
    }

    public GameSet getCurrentGameSet() {
        return currentGameSet;
    }
}
