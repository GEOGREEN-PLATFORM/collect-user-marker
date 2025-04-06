package com.example.collect_user_marker.controller;

import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.model.ResponseDTO;
import com.example.collect_user_marker.model.status.StatusDTO;
import com.example.collect_user_marker.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
@Tag(name = "Статус", description = "Позволяет управлять статусами задач")
public class StatusController {

    @Autowired
    private final StatusService statusService;

    private static final Logger logger = LoggerFactory.getLogger(UserMarkerController.class);

    @PostMapping("/create")
    @Operation(
            summary = "Создание нового статуса",
            description = "Записывает в базу данных новый статус"
    )
    public StatusEntity saveNewStatus(@RequestBody @Parameter(description = "Сущность статуса", required = true) StatusDTO statusDTO) {
        logger.info("Пролучен запрос POST /status/create на создание статуса {}", statusDTO.getCode());
        logger.debug("POST /status/create: {}", statusDTO);
        return statusService.saveNewStatus(statusDTO);
    }

    @GetMapping("/getAll")
    @Operation(
            summary = "Получить все статусы",
            description = "Позволяет получить все статусы"
    )
    public List<StatusEntity> getAllStatuses(){
        logger.info("Получен запрос GET /status/getAll");
        return statusService.getAllStatuses();
    }

    @GetMapping("/get/{id}")
    @Operation(
            summary = "Получить статус по айди"
    )
    public Optional<StatusEntity> getStatusById(@PathVariable @Parameter(description = "Уникальный айди статуса", required = true) Integer id){
        logger.info("Получен запрос GET /status/get/{}", id);
        return statusService.findStatusById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление статуса"
    )
    public ResponseDTO deleteStatus(@PathVariable @Parameter(description = "Уникальный айди статуса", required = true) Integer id) {
        logger.info("Получен запрос DELETE /status/{}", id);
        return statusService.deleteStatus(id);
    }
}
