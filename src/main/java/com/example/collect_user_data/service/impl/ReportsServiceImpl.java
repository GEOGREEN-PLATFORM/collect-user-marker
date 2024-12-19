package com.example.collect_user_data.service.impl;

import com.example.collect_user_data.entity.ReportsEntity;
import com.example.collect_user_data.repository.ReportsRepository;
import com.example.collect_user_data.service.ReportsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private ReportsRepository reportsRepository;

    @Override
    @Transactional
    public void saveNewReport(ReportsEntity reportEntity) {
        ReportsEntity newReport = reportsRepository.save(reportEntity);
        System.out.println("Saved report with id: " + newReport.getId());
    }
}
