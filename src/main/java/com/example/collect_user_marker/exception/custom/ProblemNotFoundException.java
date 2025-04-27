package com.example.collect_user_marker.exception.custom;

public class ProblemNotFoundException extends RuntimeException {
    public ProblemNotFoundException(String problemCode) {
        super("Problem " + problemCode + " not found!");
    }
}
