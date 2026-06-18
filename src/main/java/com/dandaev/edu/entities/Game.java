package com.dandaev.edu.entities;

public interface Game {
    void scorePointTo(Player player);
    boolean isOver();
    Player getWinner();
}
