package com.example.collect_user_marker.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "status")
@Data
public class StatusEntity {

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
