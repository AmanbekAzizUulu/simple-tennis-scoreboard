package com.dandaev.edu;

import com.dandaev.edu.entities.Game;
import com.dandaev.edu.entities.Player;

import java.util.Scanner;

/**
 * The type Main.
 */
public class Main {
    public static void main(String[] args) {
        Player firstPlayer = new Player("Aibek");
        Player secondPlayer = new Player("Eleonora");

        Game game = new Game(firstPlayer, secondPlayer);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Pls, type in your choice");
        System.out.println("1 --- score point to Player 1");
        System.out.println("2 --- score point to Player 2");

        String userChoice;

        while (true) {
            userChoice = scanner.nextLine();
            if (("1").equals(userChoice)) {
                game.scorePointTo(firstPlayer);
                System.out.println(game.getTable());
                if (game.isOver()) {
                    System.out.println(game.getTable());
                    break;
                }
                continue;
            }
            if (("2").equals(userChoice)){
                game.scorePointTo(secondPlayer);
                System.out.println(game.getTable());
                if (game.isOver()) {
                    System.out.println(game.getTable());
                    break;
                }
                continue;
            }
        }
    }
}
