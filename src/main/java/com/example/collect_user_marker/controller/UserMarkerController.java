package com.example.collect_user_marker.controller;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.model.UserMarkerDTO;
import com.example.collect_user_marker.service.UserMarkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Пользовательские маркеры", description = "Позволяет раборать с пользовательскими сообщениями")
public class UserMarkerController {

    @Autowired
    private final UserMarkerService userMarkerService;

    private static final Logger logger = LoggerFactory.getLogger(UserMarkerController.class);

    @PostMapping
    @Operation(
            summary = "Создание нового маркера",
            description = "Записывает в базу данных новое пользовательское сообщение"
    )
    public UserMarkerEntity saveNewReport(@RequestBody @Parameter(description = "Сущность пользовательского маркера", required = true) UserMarkerDTO userMarkerDTO) {
        logger.info("Получен запрос POST /report по координатам: {}, {}", userMarkerDTO.getCoordinate().get(0), userMarkerDTO.getCoordinate().get(1));
        logger.debug("POST /report: {}", userMarkerDTO);
        return userMarkerService.saveNewReport(userMarkerDTO);
    }

    @GetMapping("/getAll")
    @Operation(
            summary = "Получить все маркеры",
            description = "Позволяет получить все пользовательские маркеры"
    )
    public List<UserMarkerEntity> getAllReports(){
        logger.info("Получен запрос /getAll");
        return userMarkerService.getAllReports();
    }

    @GetMapping("/{reportId}")
    @Operation(
            summary = "Получить маркер по айди",
            description = "Позволяет получить маркер по айди"
    )
    public UserMarkerEntity getReportById(@PathVariable @Parameter(description = "Айди пользовательского маркера", required = true, example = "7632b748-02bf-444b-bb95-1a4e6e1cffc5") UUID reportId){
        logger.info("Получен запрос GET /{reportId} с айди: {}", reportId);
        return userMarkerService.getReportById(reportId);
    }

    @PutMapping("/{reportId}")
    @Operation(
            summary = "Обновить информацио о маркере",
            description = "Позволяет обновить информацио о маркере"
    )
    public UserMarkerEntity updateReport(@RequestBody @Parameter(description = "Сущность пользовательского маркера", required = true) UserMarkerDTO report, @PathVariable @Parameter(description = "Айди пользовательского маркера", required = true, example = "7632b748-02bf-444b-bb95-1a4e6e1cffc5") UUID reportId){
        logger.info("Получен запрос PUT /{reportId} с айди: {}", reportId);
        logger.debug("PUT /{reportId}: {}", report);
        return userMarkerService.updateReport(report, reportId);
    }
}
