package com.example.collect_user_data.service;

import com.example.collect_user_data.entity.ReportsEntity;

import java.util.List;

public interface ReportsService {

    ReportsEntity saveNewReport(ReportsEntity reportsEntity);

    List<ReportsEntity> getAllReports();

    ReportsEntity getReportById(Long id);

    ReportsEntity updateReport(ReportsEntity report, Long id);
}
