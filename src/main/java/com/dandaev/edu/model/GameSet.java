package com.dandaev.edu.model;

import com.dandaev.edu.exceptions.domain.GameSetAlreadyFinishedException;
import com.dandaev.edu.exceptions.domain.InvalidPlayerException;

import java.util.HashMap;
import java.util.Map;

public class GameSet {
    private final Player firstPlayer;
    private final Player secondPlayer;

    private Game currentGame;
    private final Map<Player, Integer> gamesWonByPlayer;

    private Player winner;

    private boolean isOver;

    private boolean isRegularGameActive;
    private boolean isTieBreakGameActive;

    public GameSet(Player firstPlayer, Player secondPlayer) {
        if (firstPlayer == null || secondPlayer == null) {
            throw new InvalidPlayerException("Player cannot be null");
        }
        if (firstPlayer.equals(secondPlayer)) {
            throw new InvalidPlayerException("Players must be different");
        }

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.currentGame = new RegularGame(firstPlayer, secondPlayer);
        this.gamesWonByPlayer = new HashMap<>();

        this.gamesWonByPlayer.put(firstPlayer, Integer.valueOf(0));
        this.gamesWonByPlayer.put(secondPlayer, Integer.valueOf(0));

        this.winner = null;

        this.isRegularGameActive = false;
        this.isTieBreakGameActive = false;
    }

    public void scorePointTo(Player player) {
        if (isOver) {
            throw new GameSetAlreadyFinishedException("Game Set`s already over");
        }

        currentGame.scorePointTo(player);

        if (currentGame.isOver()) {
            Player winner = currentGame.getWinner();
            gamesWonByPlayer.put(winner, gamesWonByPlayer.get(winner) + 1);
            if (!checkGameSetComplete()) {
                int firstPlayerGameWinsCount = gamesWonByPlayer.get(firstPlayer);
                int secondPlayerGameWinsCount = gamesWonByPlayer.get(secondPlayer);
                if (firstPlayerGameWinsCount == 6 && secondPlayerGameWinsCount == 6) {
                    isTieBreakGameActive = true;
                    currentGame = new TieBreakGame(firstPlayer, secondPlayer);
                } else {
                    isRegularGameActive = true;
                    currentGame = new RegularGame(firstPlayer, secondPlayer);
                }
            }
        }
    }

    private boolean checkGameSetComplete() {
        int firstPlayerGameWinCount = gamesWonByPlayer.get(firstPlayer);
        int secondPlayerGameWinCount = gamesWonByPlayer.get(secondPlayer);

        // нормальное флоу сетов
        if (firstPlayerGameWinCount >= 6 && firstPlayerGameWinCount - secondPlayerGameWinCount >= 2) {
            winner = firstPlayer;
            return isOver = true;
        }
        if (secondPlayerGameWinCount >= 6 && secondPlayerGameWinCount - firstPlayerGameWinCount >= 2) {
            winner = secondPlayer;
            return isOver = true;
        }
        // тай-брейк флоу сетов
        if (firstPlayerGameWinCount == 7 && secondPlayerGameWinCount == 6) {
            winner = firstPlayer;
            return isOver = true;
        }
        if (secondPlayerGameWinCount == 7 && firstPlayerGameWinCount == 6) {
            winner = secondPlayer;
            return isOver = true;
        }

        return false;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isOver() {
        return isOver;
    }

    public Map<Player, Integer> getGamesWonByPlayer() {
        return new HashMap<>(gamesWonByPlayer);
    }

    boolean isRegularGameActive() {
        return isRegularGameActive;
    }

    boolean isTieBreakGameActive() {
        return isTieBreakGameActive;
    }

    public Game getCurrentGame() { return currentGame; }

    public boolean isTieBreak() { return currentGame instanceof TieBreakGame; }
}
