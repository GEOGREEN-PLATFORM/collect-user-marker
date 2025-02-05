package com.example.collect_user_marker.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class DetailsDTO {

    private List<UUID> images;

    @NotNull
    @Pattern(regexp = "Борщевик|Пожар|Свалка", message = "Тип проблемы должен быть одним из: Борщевик, Пожар, Свалка")
    private String problemAreaType;

    @Size(max = 256)
    private String comment;

}