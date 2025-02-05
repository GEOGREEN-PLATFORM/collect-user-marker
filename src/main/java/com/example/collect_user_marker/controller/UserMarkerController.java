package com.example.collect_user_marker.controller;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.model.UserMarkerDTO;
import com.example.collect_user_marker.service.UserMarkerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class UserMarkerController {

    @Autowired
    private final UserMarkerService userMarkerService;

    private static final Logger logger = LoggerFactory.getLogger(UserMarkerController.class);

    @PostMapping
    public UserMarkerEntity saveNewReport(@RequestBody UserMarkerDTO userMarkerDTO) {
        logger.info("Получен запрос POST /report по координатам: {}, {}", userMarkerDTO.getCoordinate().get(0), userMarkerDTO.getCoordinate().get(1));
        logger.debug("POST /report: {}", userMarkerDTO);
        return userMarkerService.saveNewReport(userMarkerDTO);
    }

    @GetMapping("/getAll")
    public List<UserMarkerEntity> getAllReports(){
        logger.info("Получен запрос /getAll");
        return userMarkerService.getAllReports();
    }

    @GetMapping("/{reportId}")
    public UserMarkerEntity getReportById(@PathVariable UUID reportId){
        logger.info("Получен запрос GET /{reportId} с айди: {}", reportId);
        return userMarkerService.getReportById(reportId);
    }

    @PutMapping("/{reportId}")
    public UserMarkerEntity updateReport(@RequestBody UserMarkerDTO report, @PathVariable UUID reportId){
        logger.info("Получен запрос PUT /{reportId} с айди: {}", reportId);
        logger.debug("PUT /{reportId}: {}", report);
        return userMarkerService.updateReport(report, reportId);
    }
}
