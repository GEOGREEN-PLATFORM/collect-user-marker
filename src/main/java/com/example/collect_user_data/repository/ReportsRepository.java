package com.example.collect_user_data.repository;

import com.example.collect_user_data.entity.ReportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportsRepository extends JpaRepository<ReportsEntity, UUID> {

}