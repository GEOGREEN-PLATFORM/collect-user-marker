package com.example.collect_user_marker.entity;

import com.example.collect_user_marker.converter.ImageListConverter;
import com.example.collect_user_marker.model.UserDTO;
import com.example.collect_user_marker.model.image.ImageDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_markers")
@Data
public class UserMarkerEntity {

    @Id
    @GeneratedValue(generator = "uuid-generator")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    @NotNull
    private UUID id;

    @NotNull
    @Column(name = "coordinates")
    private List<Double> coordinates;

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "user_comment")
    @Size(max = 256)
    private String userComment;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "images", columnDefinition = "jsonb")
    @Convert(converter = ImageListConverter.class)
    private List<ImageDTO> images;

    @JoinColumn(name = "status_code", referencedColumnName = "code")
    @Column(name = "status_code")
    private String status;

    @Column(name = "create_date")
    @NotNull
    private Instant createDate;

    @Column(name = "operator_comment")
    @Size(max = 256)
    private String operatorComment;

    @Column(name = "photo_verification")
    private boolean photoVerification;

    @NotNull
    @Column(name = "photo_predictions")
    private List<Integer> photoPredictions;

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "operator", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private UserDTO operator;

    @JoinColumn(name = "problem_type", referencedColumnName = "code")
    @Column(name = "problem_type")
    private String problemAreaType;

}