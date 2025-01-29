package com.example.collect_user_marker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "UserMarkers")
@Data
public class UserMarkerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
    @SequenceGenerator(name = "id_generator", sequenceName = "user_markers_seq", allocationSize = 1)
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
    @Pattern(regexp = "НОВАЯ|НА АНАЛИЗЕ|ЗАКРЫТА", message = "Status must be one of: НОВАЯ, НА АНАЛИЗЕ, ЗАКРЫТА")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserMarkerEntity report = (UserMarkerEntity) o;

        return Objects.equals(x, report.x) &&
                Objects.equals(y, report.y) &&
                Objects.equals(userEmail, report.userEmail) &&
                Objects.equals(createDate, report.createDate) &&
                Objects.equals(userPhone, report.userPhone) &&
                Objects.equals(userComment, report.userComment);
    }

}
