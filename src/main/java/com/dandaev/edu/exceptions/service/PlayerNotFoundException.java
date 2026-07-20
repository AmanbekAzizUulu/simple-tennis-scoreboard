package com.dandaev.edu.exceptions.service;


public class PlayerNotFoundException extends ServiceException {

    public PlayerNotFoundException(String playerName) {
        super("Player '" + playerName + "' not found");
    }
}
