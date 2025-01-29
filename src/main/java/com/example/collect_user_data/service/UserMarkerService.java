package com.example.collect_user_data.service;

import com.example.collect_user_data.entity.UserMarkerEntity;

import java.util.List;

public interface UserMarkerService {

    UserMarkerEntity saveNewReport(UserMarkerEntity userMarkerEntity);

    List<UserMarkerEntity> getAllReports();

    UserMarkerEntity getReportById(Long id);

    UserMarkerEntity updateReport(UserMarkerEntity report, Long id);
}
