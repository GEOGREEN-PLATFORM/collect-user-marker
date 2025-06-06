package com.example.collect_user_marker.producer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

import static com.example.collect_user_marker.util.DateUtil.ISO_8601_DATE_TIME_MILLIS_PATTERN;
import static com.example.collect_user_marker.util.DateUtil.UTC;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateElementDTO {

    @NotNull
    private UUID elementId;

    @NotNull
    private String type;

    private String status;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = ISO_8601_DATE_TIME_MILLIS_PATTERN,
            timezone = UTC
    )
    private OffsetDateTime date;

}
