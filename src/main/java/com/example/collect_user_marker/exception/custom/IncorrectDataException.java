package com.example.collect_user_marker.exception.custom;

public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException(String message) {
        super("An error occurred - the parameters were incorrectly passed, namely " + message);
    }
}
