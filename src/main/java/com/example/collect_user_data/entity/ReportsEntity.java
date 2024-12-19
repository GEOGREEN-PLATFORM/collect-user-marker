package com.example.collect_user_data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "reports", schema = "collected-user-data-schema")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ReportsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String user_contact;

    @Column
    private Double coordinate_x;

    @Column
    private Double coordinate_y;

    @Column
    private Double area_size;

    @Column
    private boolean photo;

    @Column
    private String comment;

}
