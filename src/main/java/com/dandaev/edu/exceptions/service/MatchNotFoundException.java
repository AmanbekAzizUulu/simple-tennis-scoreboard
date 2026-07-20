package com.dandaev.edu.exceptions.service;

public class MatchNotFoundException extends ServiceException {

    public MatchNotFoundException(String uuid) {
        super("Match with UUID " + uuid + " not found");
    }
}