package com.example.collect_user_marker.model.problemType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность экологической проблемы")
public class ProblemTypeDTO {

    @NotNull
    @Size(max = 50)
    @Schema(description = "Название проблемы", example = "Загрязнение воды")
    private String code;

    @Size(max = 256)
    @Schema(description = "Описание проблемы", example = "тут лужа")
    private String description;
}
