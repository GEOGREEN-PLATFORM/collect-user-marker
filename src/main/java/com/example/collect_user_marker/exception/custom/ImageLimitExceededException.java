package com.example.collect_user_marker.exception.custom;

public class ImageLimitExceededException extends RuntimeException {
    public ImageLimitExceededException() {
        super("Превышен лимит количества изображений!");
    }
}
