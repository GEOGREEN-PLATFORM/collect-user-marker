package com.example.collect_user_marker.model.geo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Сущность для создания очага на карте")
public class GeoMarkerDTO {

    @NotEmpty
    @Schema(description = "Координаты очага", example = "[1.0, 2.0]")
    private List<Double> coordinate;

    @NotEmpty
    private GeoDetailsDTO details;
}
