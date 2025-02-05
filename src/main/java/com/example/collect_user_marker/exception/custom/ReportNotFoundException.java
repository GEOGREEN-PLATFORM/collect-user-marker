package com.example.collect_user_marker.exception.custom;

import java.util.UUID;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(UUID id) {
        super("Report with id " + id + " not found!");
    }
}
