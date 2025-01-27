package com.example.collect_user_data.service.impl;

import com.example.collect_user_data.entity.ReportsEntity;
import com.example.collect_user_data.exception.custom.IncorrectUpdateException;
import com.example.collect_user_data.exception.custom.ReportNotFoundException;
import com.example.collect_user_data.repository.ReportsRepository;
import com.example.collect_user_data.service.ReportsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private ReportsRepository reportsRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReportsServiceImpl.class);

    @Override
    @Transactional
    public ReportsEntity saveNewReport(ReportsEntity reportEntity) {
        reportEntity.setStatus("НОВАЯ");
        reportEntity.setCreateDate(LocalDate.now());

        logger.debug("Сохранена новая заявка: {}", reportEntity);

        return reportsRepository.save(reportEntity);
    }

    @Override
    public List<ReportsEntity> getAllReports() {
        return reportsRepository.findAll();
    }

    @Override
    public ReportsEntity getReportById(Long id) {
        return reportsRepository.findById(id).orElseThrow(
                () -> new ReportNotFoundException(id)
        );
    }

    @Override
    @Transactional
    public ReportsEntity updateReport(ReportsEntity newReport, Long id) {
        ReportsEntity oldReport = getReportById(id);
        if (oldReport.equals(newReport))
        {
            logger.debug("Данные по заявке с айди {} успешно обновлены", id);
            newReport.setUpdateDate(LocalDate.now());
            reportsRepository.save(newReport);
        }
        else {
            throw new IncorrectUpdateException(id);
        }
        return newReport;
    }
}
