package com.example.collect_user_data.repository;

import com.example.collect_user_data.entity.ReportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportsRepository extends JpaRepository<ReportsEntity, UUID> {

}