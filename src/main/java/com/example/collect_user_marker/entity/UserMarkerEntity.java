package com.example.collect_user_marker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

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

    @Column(name = "images", columnDefinition = "uuid[]")
    private List<UUID> images;

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

    @Column(name = "update_date")
    private Instant updateDate;

    @Column(name = "operator_name")
    @Size(max = 50)
    private String operatorName;

    @Column(name = "operator_id")
    private UUID operatorId;

    @JoinColumn(name = "problem_type", referencedColumnName = "code")
    @Column(name = "problem_type")
    private String problemAreaType;

}