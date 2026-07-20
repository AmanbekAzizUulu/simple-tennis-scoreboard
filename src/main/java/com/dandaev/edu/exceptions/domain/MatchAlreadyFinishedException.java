package com.dandaev.edu.exceptions.domain;

import com.dandaev.edu.exceptions.service.ServiceException;

public class MatchAlreadyFinishedException extends DomainException {

    public MatchAlreadyFinishedException() {
        super("Match is already over");
    }
}