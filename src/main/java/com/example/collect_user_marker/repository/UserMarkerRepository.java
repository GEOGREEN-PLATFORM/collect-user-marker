package com.example.collect_user_marker.repository;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserMarkerRepository extends JpaRepository<UserMarkerEntity, UUID> {

}