package com.example.collect_user_marker.producer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoAnalyseReqDTO {

    @NotNull
    private UUID userMarkerId;

    @NotNull
    private Integer protoPosition;

    @NotNull
    private UUID photoId;
}
