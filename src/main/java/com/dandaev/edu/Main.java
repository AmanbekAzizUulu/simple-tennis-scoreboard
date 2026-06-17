package com.dandaev.edu;

import com.dandaev.edu.entities.Counter;

public class Main {
    public static void main(String[] args) {
//        Counter counter = new Counter(0);
//        System.out.println("Increasing internal counter: ");
//        counter.increaseCount();
//        System.out.println(counter);
//        counter.increaseCount();
//        System.out.println(counter);
//
//        System.out.println("Decreasing internal counter: ");
//        counter.decreaseCount();
//        System.out.println(counter);
//        counter.decreaseCount();
//        System.out.println(counter);

        Counter counter = new Counter(0);
        System.out.println("Initial counter: " + counter.getCount());

        ConsoleCounterInteractor consoleCounterInteractor = new ConsoleCounterInteractor(counter);

        consoleCounterInteractor.startup();

        System.out.println("End program counter: " + counter.getCount());
    }
}
