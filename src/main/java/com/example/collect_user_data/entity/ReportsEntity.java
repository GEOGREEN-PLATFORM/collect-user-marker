package com.example.collect_user_data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "UserReports")
@Data
public class ReportsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "user_reports_seq", allocationSize = 1)
    private Long id;

    // TODO валидация координат, когда будет понятно, как они выглядят
    @Column
    @NotNull
    private Double x;

    @Column
    @NotNull
    private Double y;

    @Column
    @Size(max=12)
    private String userPhone;

    @Column
    @Email
    private String userEmail;

    @Column
    @Size(max=256)
    private String userComment;

    @Column
    private UUID photoId;

    @Column
    @NotBlank
    @Pattern(regexp = "NEW|ANALYSIS|PAUSED|PROCESSED", message = "Status must be one of: NEW, ANALYSIS, PAUSED, PROCESSED")
    private String status;

    @Column
    @DecimalMax("100.00")
    private Double prediction;

    @Column
    @FutureOrPresent
    @NotNull
    private LocalDate createDate;

    @Column
    @Size(max=256)
    private String operatorComment;

    @Column
    private boolean hogweed;

    @Column
    @FutureOrPresent
    private LocalDate updateDate;

}
