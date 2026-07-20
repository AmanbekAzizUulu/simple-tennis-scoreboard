package com.dandaev.edu.validators;

import com.dandaev.edu.exceptions.web.ValidationException;

public class Validator {

    public static void validatePlayerNames(String name1, String name2) {
        if (name1 == null || name1.isBlank()) {
            throw new ValidationException("Player 1 name must not be empty");
        }
        if (name2 == null || name2.isBlank()) {
            throw new ValidationException("Player 2 name must not be empty");
        }
        if (name1.equals(name2)) {
            throw new ValidationException("Players must be different");
        }
        if (name1.length() > 100 || name2.length() > 100) {
            throw new ValidationException("Player name must not exceed 100 characters");
        }
    }

    public static void validateUuid(String uuid) {
        if (uuid == null || uuid.isBlank()) {
            throw new ValidationException("Missing match UUID");
        }
        try {
            java.util.UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Invalid match UUID format");
        }
    }

    public static int validatePage(String pageParam) {
        if (pageParam == null || pageParam.isBlank()) {
            return 1;
        }
        try {
            int page = Integer.parseInt(pageParam);
            if (page < 1) {
                return 1;
            }
            return page;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}