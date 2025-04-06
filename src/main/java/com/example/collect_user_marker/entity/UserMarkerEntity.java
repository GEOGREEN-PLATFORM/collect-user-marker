package com.example.collect_user_marker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

    @Column
    @NotNull
    private Double x;

    @Column
    @NotNull
    private Double y;

    @Column(name = "user_phone")
    @Size(max = 12)
    private String userPhone;

    @Column(name = "user_email")
    @Email
    private String userEmail;

    @Column(name = "user_comment")
    @Size(max = 256)
    private String userComment;

    @Column(name = "images", columnDefinition = "uuid[]")
    private List<UUID> images;

    @ManyToOne
    @JoinColumn(name = "status_code", referencedColumnName = "code")
    private StatusEntity status;

    @Column(name = "create_date")
    @FutureOrPresent
    @NotNull
    private LocalDate createDate;

    @Column(name = "operator_comment")
    @Size(max = 256)
    private String operatorComment;

    @Column(name = "photo_verification")
    private boolean photoVerification;

    @Column(name = "update_date")
    @FutureOrPresent
    private LocalDate updateDate;

    @Column(name = "problem_area_type")
    @Pattern(regexp = "Борщевик|Пожар|Свалка", message = "Status must be one of: Борщевик, Пожар, Свалка")
    private String problemAreaType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserMarkerEntity report = (UserMarkerEntity) o;

        return Objects.equals(x, report.x) &&
                Objects.equals(y, report.y) &&
                Objects.equals(userEmail, report.userEmail) &&
                Objects.equals(userPhone, report.userPhone) &&
                Objects.equals(userComment, report.userComment);
    }
}