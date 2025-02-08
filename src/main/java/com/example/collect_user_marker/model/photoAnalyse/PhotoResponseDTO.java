package com.example.collect_user_marker.model.photoAnalyse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoResponseDTO {

    private Boolean isHogweed;

    private Float prediction;
}
