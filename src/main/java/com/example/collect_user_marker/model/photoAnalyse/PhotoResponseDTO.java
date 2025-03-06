package com.example.collect_user_marker.model.photoAnalyse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность возвращаемого ответа по фото")
public class PhotoResponseDTO {

    @Schema(description = "Является ли фото борщевиком", example = "true")
    private Boolean isHogweed;

    @Schema(description = "Вероятность того, является ли фото борщевиком", example = "12.5")
    private Float prediction;
}
