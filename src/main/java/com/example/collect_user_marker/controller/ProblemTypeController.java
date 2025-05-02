package com.example.collect_user_marker.controller;

import com.example.collect_user_marker.entity.ProblemTypeEntity;
import com.example.collect_user_marker.model.ResponseDTO;
import com.example.collect_user_marker.model.problemType.ProblemTypeDTO;
import com.example.collect_user_marker.service.ProblemTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.collect_user_marker.util.AuthorizationStringUtil.*;

@RestController
@RequestMapping("/user-marker/problemType")
@RequiredArgsConstructor
@SecurityRequirement(name = AUTHORIZATION)
@Tag(name = "Тип проблемы", description = "Позволяет управлять типами экологических проблем")
public class ProblemTypeController {

    @Autowired
    private final ProblemTypeService problemTypeService;

    private static final Logger logger = LoggerFactory.getLogger(UserMarkerController.class);

    @PostMapping("/create")
    @Operation(
            summary = "Создание нового типа проблем",
            description = "Записывает в базу данных новый тип экологической проблемы"
    )
    @RolesAllowed({ADMIN, OPERATOR})
    public ProblemTypeEntity saveNewProblem(@RequestBody @Parameter(description = "Сущность экологической проблемы", required = true) ProblemTypeDTO problemTypeDTO) {
        logger.info("Пролучен запрос POST /problemType/create на создание проблемы {}", problemTypeDTO.getCode());
        logger.debug("POST /problemType/create - {}", problemTypeDTO);
        return problemTypeService.saveNewProblem(problemTypeDTO);
    }

    @GetMapping("/getAll")
    @Operation(
            summary = "Получить все проблемы"
    )
    @RolesAllowed({ADMIN, OPERATOR})
    public List<ProblemTypeEntity> getAllProblems() {
        logger.info("Получен запрос GET /problemType/getAll");
        return problemTypeService.getAllProblems();
    }

    @GetMapping("/get/{id}")
    @Operation(
            summary = "Получить экологическую проблему по айди"
    )
    @RolesAllowed({ADMIN, OPERATOR})
    public Optional<ProblemTypeEntity> getProblemById(@PathVariable @Parameter(description = "Уникальный айди проблемы", required = true) Integer id) {
        logger.info("Получен запрос GET /problemType/get/{}", id);
        return problemTypeService.getProblemById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление проблемы"
    )
    @RolesAllowed({ADMIN, OPERATOR})
    public ResponseDTO deleteProblemById(@PathVariable @Parameter(description = "Уникальный айди проблемы", required = true) Integer id) {
        logger.info("Получен запрос DELETE /problemType/{}", id);
        return problemTypeService.deleteProblemById(id);
    }

}
