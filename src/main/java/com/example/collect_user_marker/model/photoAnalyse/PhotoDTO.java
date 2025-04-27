package com.example.collect_user_marker.model.photoAnalyse;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(description = "Сущность фотографии")
public class PhotoDTO {

    @NotNull
    @NotBlank
    @Schema(description = "Айди фотографии", example = "d2f0887f-6b1d-4ee0-aba9-9e006bc4745e")
    private UUID photoId;
}