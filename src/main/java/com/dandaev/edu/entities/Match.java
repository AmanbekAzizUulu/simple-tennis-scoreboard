package com.dandaev.edu.entities;

import java.util.HashMap;
import java.util.Map;

public class Match implements Game {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private Player winner;

    private final Map<Player, Integer> setsWinByPlayers;

    private boolean isOver;

    private GameSet gameSet;


    public Match(Player firstPlayer, Player secondPlayer) {
        if (firstPlayer == null || secondPlayer == null) {
            throw new IllegalArgumentException("Player can't be null");
        }
        if (firstPlayer.equals(secondPlayer)) {
            throw new IllegalArgumentException("Players must be different");
        }

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.setsWinByPlayers = new HashMap<>();

        this.isOver = false;

        this.gameSet = new GameSet(firstPlayer, secondPlayer);

        this.setsWinByPlayers.put(firstPlayer, Integer.valueOf(0));
        this.setsWinByPlayers.put(secondPlayer, Integer.valueOf(0));
    }


    @Override
    public void scorePointTo(Player player) {
        if (isOver) {
            throw new IllegalStateException("Game`s already over");
        }
        if (!player.equals(firstPlayer) && !player.equals(secondPlayer)) {
            throw new IllegalArgumentException("Unknown player");
        }

        gameSet.scorePointTo(player);

        if (gameSet.isOver()) {
            Player gameSetWinner = gameSet.getWinner();
            setsWinByPlayers.put(gameSetWinner, setsWinByPlayers.get(gameSetWinner) + 1);
            if (!checkMatchComplete()) {
                gameSet = new GameSet(firstPlayer, secondPlayer);
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

    Map<Player, Integer> getSetsWinByPlayers() {
        return setsWinByPlayers;
    }

    int getSetsWonBy(Player player) {
        Integer sets = setsWinByPlayers.get(player);

        if (sets == null) {
            throw new IllegalArgumentException("Unknown player");
        }

        return sets;
    }
}
