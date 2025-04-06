package com.example.collect_user_marker.service;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.model.OperatorDetailsDTO;
import com.example.collect_user_marker.model.UserMarkerDTO;

import java.util.List;
import java.util.UUID;

public interface UserMarkerService {

    UserMarkerEntity saveNewReport(UserMarkerDTO userMarkerDTO);

    List<UserMarkerEntity> getAllReports();

    UserMarkerEntity getReportById(UUID id);

    UserMarkerEntity updateReport(OperatorDetailsDTO operatorDetailsDTO, UUID id);
}
