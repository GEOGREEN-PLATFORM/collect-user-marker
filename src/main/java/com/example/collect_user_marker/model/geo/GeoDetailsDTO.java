package com.example.collect_user_marker.model.geo;

import com.example.collect_user_marker.model.image.ImageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Сущность детальной информации об очаге")
public class GeoDetailsDTO {

    @NotEmpty
    @Schema(example = "Требует заполнения сотрудником")
    private String owner;

    @NotEmpty
    @Schema(example = "Требует заполнения сотрудником")
    private String contractingOrganization;

    @NotEmpty
    @Schema(description = "Статус очага", example = "Создано")
    private String workStage;

    @Schema(description = "Лист айди фотографий борщевика")
    private List<ImageDTO> images;

    @NotNull
    @Size(max = 50)
    @Schema(description = "Тип экологической проблемы", example = "Борщевик")
    private String problemAreaType;

    @Size(max = 256)
    @Schema(description = "Комментарий оператора", example = "тут много борщевика")
    private String comment;
}
