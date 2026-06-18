package com.dandaev.edu;

import com.dandaev.edu.entities.GameSet;
import com.dandaev.edu.entities.Player;

/**
 * The type Main.
 */
public class Main {
    public static void main(String[] args) {
        Player firstPlayer = new Player("Aibek");
        Player secondPlayer = new Player("Eleonora");

        GameSet gameSet = new GameSet(firstPlayer, secondPlayer);

        // выиграть игру - 1
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);

        // выиграть игру - 2
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);

        // выиграть игру - 3
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);

        // выиграть игру - 4
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);

        // выиграть игру - 5
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);

        // выиграть игру - 6
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);
        gameSet.pointWon(firstPlayer);

        // выиграть сет


        System.out.println(gameSet.getGameSetWinner());


    }
}
