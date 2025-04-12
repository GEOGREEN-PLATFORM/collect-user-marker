package com.example.collect_user_marker.service;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.model.OperatorDetailsDTO;
import com.example.collect_user_marker.model.UserMarkerDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserMarkerService {

    UserMarkerEntity saveNewReport(UserMarkerDTO userMarkerDTO, String token);

    Page<UserMarkerEntity> getAllReports(int page, int size);

    UserMarkerEntity getReportById(UUID id);

    UserMarkerEntity updateReport(OperatorDetailsDTO operatorDetailsDTO, UUID id);
}
