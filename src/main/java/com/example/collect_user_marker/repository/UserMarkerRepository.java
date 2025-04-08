package com.example.collect_user_marker.repository;

import com.example.collect_user_marker.entity.ProblemTypeEntity;
import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.entity.UserMarkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserMarkerRepository extends JpaRepository<UserMarkerEntity, UUID> {

    @Transactional
    @Modifying
    @Query("UPDATE UserMarkerEntity u SET u.status = :newStatus WHERE u.status = :oldStatus")
    int updateStatusForMarkers(String oldStatus, String newStatus);

    @Transactional
    @Modifying
    @Query("UPDATE UserMarkerEntity u SET u.problemAreaType = :newProblem WHERE u.problemAreaType = :oldProblem")
    int updateProblemForMarkers(String oldProblem, String newProblem);

}