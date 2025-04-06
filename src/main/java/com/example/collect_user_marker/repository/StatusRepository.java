package com.example.collect_user_marker.repository;

import com.example.collect_user_marker.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
    StatusEntity findByCode(String code);
    @Query("SELECT s FROM StatusEntity s WHERE s.isDefault = true")
    StatusEntity findDefaultStatus();
    void deleteByCode(String code);
}