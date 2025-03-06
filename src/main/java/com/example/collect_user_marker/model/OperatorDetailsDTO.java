package com.example.collect_user_marker.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность статуса сообщения пользователя")
public class OperatorDetailsDTO {

    @Pattern(regexp = "НОВАЯ|НА АНАЛИЗЕ|ЗАКРЫТА", message = "Статус должен быть одним из: НОВАЯ, НА АНАЛИЗЕ, ЗАКРЫТА")
    @Schema(description = "Статус сообщения", allowableValues = {"НОВАЯ", "НА АНАЛИЗЕ", "ЗАКРЫТА"})
    private String status;

    @Size(max = 256)
    @Schema(description = "Комментарий оператора", example = "тут много борщевика")
    private String operatorComment;
}