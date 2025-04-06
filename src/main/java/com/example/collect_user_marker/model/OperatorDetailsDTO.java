package com.example.collect_user_marker.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность статуса сообщения пользователя")
public class OperatorDetailsDTO {

    @Size(max = 50)
    @Schema(description = "Статус сообщения", example = "В РАБОТЕ")
    private String statusCode;

    @Size(max = 256)
    @Schema(description = "Комментарий оператора", example = "тут много борщевика")
    private String operatorComment;
}