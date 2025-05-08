package com.example.collect_user_marker.controller;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.model.OperatorDetailsDTO;
import com.example.collect_user_marker.model.UserMarkerDTO;
import com.example.collect_user_marker.service.UserMarkerService;
import com.example.collect_user_marker.util.pagination.SimplifiedPageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

import static com.example.collect_user_marker.util.AuthorizationStringUtil.*;

@RestController
@RequestMapping("/user-marker")
@RequiredArgsConstructor
@SecurityRequirement(name = AUTHORIZATION)
@Tag(name = "Пользовательские маркеры", description = "Позволяет раборать с пользовательскими сообщениями")
public class UserMarkerController {

    @Autowired
    private final UserMarkerService userMarkerService;

    private static final Logger logger = LoggerFactory.getLogger(UserMarkerController.class);

    @PostMapping("/report")
    @Operation(
            summary = "Создание нового маркера",
            description = "Записывает в базу данных новое пользовательское сообщение"
    )
    @RolesAllowed({USER})
    public UserMarkerEntity saveNewReport(@RequestHeader("Authorization") String token, @RequestBody @Parameter(description = "Сущность пользовательского маркера", required = true) UserMarkerDTO userMarkerDTO) {
        logger.info("Получен запрос POST /report по координатам: {}, {}", userMarkerDTO.getCoordinate().get(0), userMarkerDTO.getCoordinate().get(1));
        logger.debug("POST /report: {}", userMarkerDTO);
        return userMarkerService.saveNewReport(userMarkerDTO, token);
    }

    @GetMapping("/getAll")
    @Operation(
            summary = "Получить все маркеры",
            description = "Позволяет получить все пользовательские маркеры"
    )
    public SimplifiedPageResponse<UserMarkerEntity> getAllReports(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String problemType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updateDate") String sortField,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection){
        logger.info("Получен запрос /getAll");
        Page<UserMarkerEntity> result = userMarkerService.getAllReports(token, page, size, problemType, startDate, endDate, sortField, sortDirection);
        return new SimplifiedPageResponse<>(result);
    }

    @GetMapping("/{reportId}")
    @Operation(
            summary = "Получить маркер по айди",
            description = "Позволяет получить маркер по айди"
    )
    @RolesAllowed({ADMIN, OPERATOR})
    public UserMarkerEntity getReportById(@PathVariable @Parameter(description = "Айди пользовательского маркера", required = true, example = "7632b748-02bf-444b-bb95-1a4e6e1cffc5") UUID reportId){
        logger.info("Получен запрос GET /{reportId} с айди: {}", reportId);
        return userMarkerService.getReportById(reportId);
    }

    @PatchMapping("/{reportId}")
    @Operation(
            summary = "Обновить информацио о маркере",
            description = "Позволяет обновить информацио о маркере"
    )
    @RolesAllowed({ADMIN, OPERATOR})
    public UserMarkerEntity updateReport(@RequestHeader("Authorization") String token, @RequestBody @Parameter(description = "статуса сообщения пользователя", required = true) OperatorDetailsDTO operatorDetailsDTO, @PathVariable @Parameter(description = "Айди пользовательского маркера", required = true, example = "7632b748-02bf-444b-bb95-1a4e6e1cffc5") String reportId){
        logger.info("Получен запрос PUT /{reportId} с айди: {}", reportId);
        logger.debug("PUT /{reportId}: {}", operatorDetailsDTO);
        return userMarkerService.updateReport(operatorDetailsDTO, UUID.fromString(reportId), token);
    }
}
