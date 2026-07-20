package com.dandaev.edu.exceptions.domain;

public class GameSetAlreadyFinishedException extends DomainException{

    public GameSetAlreadyFinishedException(String message) {
        super(message);
    }
}
