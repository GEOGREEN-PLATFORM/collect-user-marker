package com.example.collect_user_data.controller;

import com.example.collect_user_data.entity.ReportsEntity;
import com.example.collect_user_data.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportsController {

    @Autowired
    private final ReportsService reportsService;

    private static final Logger logger = LoggerFactory.getLogger(ReportsController.class);

    @PostMapping
    public ReportsEntity saveNewReport(@RequestBody ReportsEntity reportEntity) {
        logger.info("Получен запрос POST /report с айди: {}", reportEntity.getId());
        logger.debug("POST /report: {}", reportEntity);
        return reportsService.saveNewReport(reportEntity);
    }

    @GetMapping("/getAll")
    public List<ReportsEntity> getAllReports(){
        logger.info("Получен запрос /getAll");
        return reportsService.getAllReports();
    }

    @GetMapping("/{reportId}")
    public ReportsEntity getReportById(@PathVariable Long reportId){
        logger.info("Получен запрос GET /{reportId} с айди: {}", reportId);
        return reportsService.getReportById(reportId);
    }

    @PutMapping("/{reportId}")
    public ReportsEntity updateReport(@RequestBody ReportsEntity report, @PathVariable Long reportId){
        logger.info("Получен запрос PUT /{reportId} с айди: {}", reportId);
        logger.debug("PUT /{reportId}: {}", report);
        return reportsService.updateReport(report, reportId);
    }
}
