package com.example.collect_user_data.service.impl;

import com.example.collect_user_data.entity.UserMarkerEntity;
import com.example.collect_user_data.exception.custom.IncorrectUpdateException;
import com.example.collect_user_data.exception.custom.ReportNotFoundException;
import com.example.collect_user_data.repository.UserMarkerRepository;
import com.example.collect_user_data.service.UserMarkerService;
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
public class UserMarkerServiceImpl implements UserMarkerService {

    @Autowired
    private UserMarkerRepository userMarkerRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserMarkerServiceImpl.class);

    @Override
    @Transactional
    public UserMarkerEntity saveNewReport(UserMarkerEntity reportEntity) {
        reportEntity.setStatus("НОВАЯ");
        reportEntity.setCreateDate(LocalDate.now());

        logger.debug("Сохранена новая заявка: {}", reportEntity);

        return userMarkerRepository.save(reportEntity);
    }

    @Override
    public List<UserMarkerEntity> getAllReports() {
        return userMarkerRepository.findAll();
    }

    @Override
    public UserMarkerEntity getReportById(Long id) {
        return userMarkerRepository.findById(id).orElseThrow(
                () -> new ReportNotFoundException(id)
        );
    }

    @Override
    @Transactional
    public UserMarkerEntity updateReport(UserMarkerEntity newReport, Long id) {
        UserMarkerEntity oldReport = getReportById(id);
        if (oldReport.equals(newReport))
        {
            logger.debug("Данные по заявке с айди {} успешно обновлены", id);
            newReport.setUpdateDate(LocalDate.now());
            userMarkerRepository.save(newReport);
        }
        else {
            throw new IncorrectUpdateException(id);
        }
        return newReport;
    }
}
