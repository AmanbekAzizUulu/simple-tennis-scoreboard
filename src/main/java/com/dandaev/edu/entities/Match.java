package com.dandaev.edu.entities;

import java.util.HashMap;
import java.util.Map;

public class Match implements Game{
    private final Player firstPlayer;
    private final Player secondPlayer;
    private Player winner;

    private final Map<Player, Integer> winSetCount;

    private boolean isOver;

    private GameSet currentSet;


    public Match(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.winSetCount = new HashMap<>();

        this.isOver = false;

        this.currentSet = new GameSet(firstPlayer, secondPlayer);

        this.winSetCount.put(firstPlayer, Integer.valueOf(0));
        this.winSetCount.put(secondPlayer, Integer.valueOf(0));
    }


    public void scorePointTo(Player player) {
        if (isOver) {
            System.out.println("Match`s Over");
            return;
        }
        if (!player.equals(firstPlayer) && !player.equals(secondPlayer)) {
            return;
        }

        currentSet.pointWon(player);
        if (currentSet.isOver()) {
            Player gameSetWinner = currentSet.getWinner();
            winSetCount.put(gameSetWinner, winSetCount.get(gameSetWinner) + 1);
            if (!checkMatchComplete()) {
                currentSet = new GameSet(firstPlayer, secondPlayer);
            }
        }
    }

    private boolean checkMatchComplete() {
        if (winSetCount.get(firstPlayer) == 2) {
            winner = firstPlayer;
            return isOver = true;
        }
        if (winSetCount.get(secondPlayer) == 2) {
            winner = secondPlayer;
            return isOver = true;
        }
        return false;
    }

    public boolean isOver() {
        return isOver;
    }

    public Player getWinner() {
        return winner;
    }

    public Map<Player, Integer> getWinSetCount() {
        return winSetCount;
    }
}
