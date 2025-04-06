package com.example.collect_user_marker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "problem_type")
@Data
public class ProblemTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;
}
