package com.dandaev.edu.entities;

public class TieBreakGame implements Game {
    private int firstPlayerPoints;
    private int secondPlayerPoints;

    private final Player firstPlayer;
    private final Player secondPlayer;

    private boolean isOver;

    private Player winner;

    public TieBreakGame(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;

        this.isOver = false;

        this.winner = null;

        this.firstPlayerPoints = 0;
        this.secondPlayerPoints = 0;
    }

    public void scorePointTo(Player player) {
        // если игра окончена
        if (isOver) {
            System.out.println("Tie Break RegularGame`s Over");
            return;
        }

        // если переданный в качестве аргумента объект не является ни firstPlayer ни secondPlayer
        if (!player.equals(firstPlayer) && !player.equals(secondPlayer)) {
            System.out.println("");
            return;
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
            return;
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public Player getWinner() {
        return winner;
    }
}
