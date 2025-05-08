package com.example.collect_user_marker.service;

import com.example.collect_user_marker.consumer.dto.PhotoAnalyseRespDTO;
import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.model.OperatorDetailsDTO;
import com.example.collect_user_marker.model.UserMarkerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.util.UUID;

public interface UserMarkerService {

    UserMarkerEntity saveNewReport(UserMarkerDTO userMarkerDTO, String token);

    Page<UserMarkerEntity> getAllReports(String token, int page, int size, String problemType, Instant startDate, Instant endDate, String sortField, Sort.Direction sortDirection);

    UserMarkerEntity getReportById(UUID id);

    UserMarkerEntity updateReport(OperatorDetailsDTO operatorDetailsDTO, UUID id, String token);

    void updatePhotoVerification(PhotoAnalyseRespDTO photo);
}
