package com.example.collect_user_marker.service;

import com.example.collect_user_marker.entity.UserMarkerEntity;

import java.util.List;

public interface UserMarkerService {

    UserMarkerEntity saveNewReport(UserMarkerEntity userMarkerEntity);

    List<UserMarkerEntity> getAllReports();

    UserMarkerEntity getReportById(Long id);

    UserMarkerEntity updateReport(UserMarkerEntity report, Long id);
}
