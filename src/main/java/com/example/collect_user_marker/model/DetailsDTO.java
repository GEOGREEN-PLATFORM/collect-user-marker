package com.example.collect_user_marker.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Schema(description = "Сущность деталей о сообщении пользователя")
public class DetailsDTO {

    @Schema(description = "Лист айди фотографий борщевика", example = "[7632b748-02bf-444b-bb95-1a4e6e1cffc5]")
    private List<UUID> images;

    @NotNull
    @Size(max = 50)
    @Schema(description = "Тип экологической проблемы", example = "Борщевик")
    private String problemAreaCode;

    @Size(max = 256)
    @Schema(description = "Комментарий пользователя", example = "тут много борщевика")
    private String comment;

}