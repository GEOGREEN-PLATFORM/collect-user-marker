package com.example.collect_user_marker.model.photoAnalyse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PhotoDTO {

    @NotNull
    @NotBlank
    private UUID photoId;
}