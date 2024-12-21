package com.example.collect_user_data.service.impl;

import com.example.collect_user_data.entity.ReportsEntity;
import com.example.collect_user_data.repository.ReportsRepository;
import com.example.collect_user_data.service.ReportsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private ReportsRepository reportsRepository;

    @Override
    @Transactional
    public void saveNewReport(ReportsEntity reportEntity) {
        reportEntity.setStatus("NEW");
        reportEntity.setCreateDate(LocalDate.now());

        ReportsEntity savedReport = reportsRepository.save(reportEntity);
        System.out.println("Saved report with id: " + savedReport.getId());
    }
}
