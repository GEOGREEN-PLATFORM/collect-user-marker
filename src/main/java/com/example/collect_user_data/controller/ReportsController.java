package com.example.collect_user_data.controller;

import com.example.collect_user_data.entity.ReportsEntity;
import com.example.collect_user_data.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportsController {

    @Autowired
    private final ReportsService reportsService;

    @PostMapping
    public ReportsEntity saveNewReport(@RequestBody ReportsEntity reportEntity) {
        return reportsService.saveNewReport(reportEntity);
    }

    @GetMapping("/getAll")
    public List<ReportsEntity> getAllReports(){
        return reportsService.getAllReports();
    }

    @GetMapping("/{reportId}")
    public ReportsEntity getReportById(@PathVariable Long reportId){
        return reportsService.getReportById(reportId);
    }

    @PutMapping("/{reportId}")
    public ReportsEntity updateReport(@RequestBody ReportsEntity report, @PathVariable Long reportId){
        return reportsService.updateReport(report, reportId);
    }
}
