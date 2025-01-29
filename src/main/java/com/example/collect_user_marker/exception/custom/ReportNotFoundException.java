package com.example.collect_user_marker.exception.custom;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(Long id) {
        super("Report with id " + id + " not found!");
    }
}
