package com.example.collect_user_marker.repository;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserMarkerRepository extends JpaRepository<UserMarkerEntity, UUID>, JpaSpecificationExecutor<UserMarkerEntity> {

    @Transactional
    @Modifying
    @Query("UPDATE UserMarkerEntity u SET u.status = :newStatus WHERE u.status = :oldStatus")
    int updateStatusForMarkers(String oldStatus, String newStatus);

    @Transactional
    @Modifying
    @Query("UPDATE UserMarkerEntity u SET u.problemAreaType = :newProblem WHERE u.problemAreaType = :oldProblem")
    int updateProblemForMarkers(String oldProblem, String newProblem);

    @Modifying
    @Query(value = "UPDATE user_markers SET photo_predictions = jsonb_set(" +
            "photo_predictions::jsonb, " +
            "array[?2::text], " +
            "to_jsonb(?3)) " +
            "WHERE id = ?1", nativeQuery = true)
    @Transactional
    void updateListElement(UUID id, int index, int newValue);

    @Query("SELECT m FROM UserMarkerEntity m WHERE m.userId = :userId")
    Page<UserMarkerEntity> findByUserId(
            Specification<UserMarkerEntity> spec,
            Pageable pageable,
            @Param("userId") UUID userId
    );
}