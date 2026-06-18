package com.dandaev.edu.entities;

public class Player {
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{ " + name + " }";
    }
}
