package com.example.collect_user_data.exception.custom;

public class IncorrectUpdateException extends RuntimeException {
    public IncorrectUpdateException(Long id) {
        super("Incorrect record update error. " +
                "It occurs either in a situation where you are trying to update " +
                "one of the following fields - x, y, userEmail, userPhone, userComment, createDate - or " +
                "because of an incorrectly specified id - " + id);
    }
}
