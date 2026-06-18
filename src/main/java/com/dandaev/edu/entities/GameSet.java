package com.dandaev.edu.entities;

import java.util.HashMap;
import java.util.Map;

public class GameSet {
    private final Player firstPlayer;
    private final Player secondPlayer;

    private Game currentGame;
    private final Map<Player, Integer> gamesWonByPlayer;

    private Player winner;

    private boolean isOver;

    public GameSet(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.currentGame = new RegularGame(firstPlayer, secondPlayer);
        this.gamesWonByPlayer = new HashMap<>();

        this.gamesWonByPlayer.put(firstPlayer, Integer.valueOf(0));
        this.gamesWonByPlayer.put(secondPlayer, Integer.valueOf(0));

        this.winner = null;
    }

    public void pointWon(Player player){
        if (isOver){
            System.out.println("Set`s over");
            return;
        }

        currentGame.scorePointTo(player);

        if (currentGame.isOver()){
            Player winner = currentGame.getWinner();
            gamesWonByPlayer.put(winner, gamesWonByPlayer.get(winner) + 1);
            if (!checkGameSetComplete()){
                int firstPlayerGameWinsCount = gamesWonByPlayer.get(firstPlayer);
                int secondPlayerGameWinsCount = gamesWonByPlayer.get(secondPlayer);
                if (firstPlayerGameWinsCount == 6 && secondPlayerGameWinsCount == 6){
                    currentGame = new TieBreakGame(firstPlayer, secondPlayer);
                } else {
                    currentGame = new RegularGame(firstPlayer, secondPlayer);
                }
            }
        }
    }

    private boolean checkGameSetComplete(){
        int firstPlayerGameWinCount = gamesWonByPlayer.get(firstPlayer);
        int secondPlayerGameWinCount = gamesWonByPlayer.get(secondPlayer);

        // нормальное флоу сетов
        if (firstPlayerGameWinCount >= 6 && firstPlayerGameWinCount - secondPlayerGameWinCount >= 2){
            winner = firstPlayer;
            return isOver = true;
        }
        if (secondPlayerGameWinCount >= 6 && secondPlayerGameWinCount - firstPlayerGameWinCount >= 2){
            winner = secondPlayer;
            return isOver = true;
        }
        // тай-брейк флоу сетов
        if (firstPlayerGameWinCount == 7 && secondPlayerGameWinCount == 6){
            winner = firstPlayer;
            return isOver = true;
        }
        if (secondPlayerGameWinCount == 7 && firstPlayerGameWinCount == 6){
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
}
