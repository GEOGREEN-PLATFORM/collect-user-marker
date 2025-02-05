package com.example.collect_user_marker.exception.custom;

import java.util.UUID;

public class IncorrectUpdateException extends RuntimeException {
    public IncorrectUpdateException(UUID id) {
        super("Incorrect record update error. " +
                "It occurs either in a situation where you are trying to update " +
                "one of the following fields - x, y, userEmail, userPhone, userComment, createDate - or " +
                "because of an incorrectly specified id - " + id);
    }
}
