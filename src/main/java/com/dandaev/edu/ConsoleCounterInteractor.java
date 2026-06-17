package com.dandaev.edu;

import com.dandaev.edu.entities.Counter;

import java.util.Scanner;

public class ConsoleCounterInteractor {
    private final Scanner scanner;
    private final Counter counter;


    public ConsoleCounterInteractor(Counter counter) {
        this.scanner = new Scanner(System.in);
        this.counter = counter;
    }

    public void startup() {
        printMainMenu();
        handleUserInput();
    }

    private void printMainMenu() {
        System.out.println("Please, type in: ");
        System.out.println(" +---+------------------+");
        System.out.println(" | + | increase counter |");
        System.out.println(" | - | decrease counter |");
        System.out.println(" | 0 | quit             |");
        System.out.println(" +---+------------------+");
    }

    private void handleUserInput() {
        while (true) {
            printCounterInfo();

            String userChoice = scanner.nextLine();

            if ("+".equals(userChoice)) {
                counter.increaseCount();
                continue;
            }
            if ("-".equals(userChoice)) {
                counter.decreaseCount();
                continue;
            }
            if ("0".equals(userChoice)) {
                break;
            }
        }
    }

    private void printCounterInfo() {
        System.out.println("Counter: " + counter.getCount());
    }
}
