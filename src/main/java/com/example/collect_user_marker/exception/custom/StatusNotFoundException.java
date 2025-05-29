package com.example.collect_user_marker.exception.custom;

public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(String statusCode) {
        super("Status " + statusCode + " not found!");
    }
}
