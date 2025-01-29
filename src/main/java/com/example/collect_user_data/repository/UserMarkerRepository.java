package com.example.collect_user_data.repository;

import com.example.collect_user_data.entity.UserMarkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMarkerRepository extends JpaRepository<UserMarkerEntity, Long> {

}