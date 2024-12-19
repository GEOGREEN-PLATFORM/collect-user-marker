package com.example.collect_user_data.controller;

import com.example.collect_user_data.entity.ReportsEntity;
import com.example.collect_user_data.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @PostMapping
    public String saveNewReport(@RequestBody ReportsEntity reportEntity) {
        reportsService.saveNewReport(reportEntity);
        return "Report was saved successfully!";
    }
}
