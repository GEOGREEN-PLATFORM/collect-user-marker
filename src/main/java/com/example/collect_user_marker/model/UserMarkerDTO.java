package com.example.collect_user_marker.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserMarkerDTO {

    @NotEmpty
    private List<Double> coordinate;

    @NotEmpty
    private DetailsDTO details;

    private UserDetailsDTO userDetails;

    private OperatorDetailsDTO operatorDetails;
}