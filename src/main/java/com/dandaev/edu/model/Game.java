package com.dandaev.edu.model;

public interface Game {
    void scorePointTo(Player player);
    boolean isOver();
    Player getWinner();
}
