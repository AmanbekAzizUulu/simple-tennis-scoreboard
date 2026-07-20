package com.dandaev.edu.model;

public class Player {
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{ " + name + " }";
    }

    public String getName() {
        return name;
    }
}
