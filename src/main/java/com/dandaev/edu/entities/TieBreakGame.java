package com.dandaev.edu.entities;

public class TieBreakGame implements Game {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private int firstPlayerPoints;
    private int secondPlayerPoints;
    private boolean isOver;

    private Player winner;

    public TieBreakGame(Player firstPlayer, Player secondPlayer) {
        if (firstPlayer == null || secondPlayer == null) {
            throw new IllegalArgumentException("Player can't be null");
        }
        if (firstPlayer.equals(secondPlayer)) {
            throw new IllegalArgumentException("Players must be different");
        }

        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.isOver = false;

        this.winner = null;

        this.firstPlayerPoints = 0;
        this.secondPlayerPoints = 0;
    }

    @Override
    public void scorePointTo(Player player) {
        // если игра окончена
        if (isOver) {
            throw new IllegalStateException("Game`s already over");
        }

        // если переданный в качестве аргумента объект не является ни firstPlayer ни secondPlayer
        if (!player.equals(firstPlayer) && !player.equals(secondPlayer)) {
            throw new IllegalArgumentException("Unknown player");
        }

        // механика инкрементирования очка
        if (player.equals(firstPlayer)) {
            firstPlayerPoints++;
        }
        if (player.equals(secondPlayer)) {
            secondPlayerPoints++;
        }

        // если у первого игрока количество очков будет равно или превысит 7 и в то же время
        // если у второго игрока количество очков будет на 2 раза меньше — то выигрывает первый игрок
        if (firstPlayerPoints >= 7 && firstPlayerPoints - secondPlayerPoints >= 2) {
            winner = firstPlayer;
            isOver = true;
            return;
        }
        // если у второго игрока количество очков будет равно или превысит 7 и в то же время
        // если у первого игрока количество очков будет на 2 раза меньше — то выигрывает второй игрок
        if (secondPlayerPoints >= 7 && secondPlayerPoints - firstPlayerPoints >= 2) {
            winner = secondPlayer;
            isOver = true;
        }
    }

    @Override
    public boolean isOver() {
        return isOver;
    }
    @Override
    public Player getWinner() {
        return winner;
    }

    int getFirstPlayerPoints() {
        return firstPlayerPoints;
    }

    int getSecondPlayerPoints() {
        return secondPlayerPoints;
    }
}
