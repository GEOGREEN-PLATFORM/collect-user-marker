package com.example.collect_user_marker.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperatorDetailsDTO {

    @Pattern(regexp = "НОВАЯ|НА АНАЛИЗЕ|ЗАКРЫТА", message = "Статус должен быть одним из: НОВАЯ, НА АНАЛИЗЕ, ЗАКРЫТА")
    private String status;

    @Size(max = 256)
    private String operatorComment;
}