package com.example.collect_user_marker.service.impl;

import com.example.collect_user_marker.entity.ProblemTypeEntity;
import com.example.collect_user_marker.model.ResponseDTO;
import com.example.collect_user_marker.model.problemType.ProblemTypeDTO;
import com.example.collect_user_marker.repository.ProblemTypeRepository;
import com.example.collect_user_marker.repository.UserMarkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProblemTypeServiceImplTest {

    private ProblemTypeRepository problemTypeRepository;
    private UserMarkerRepository userMarkerRepository;
    private ProblemTypeServiceImpl service;

    @BeforeEach
    void setUp() {
        problemTypeRepository = mock(ProblemTypeRepository.class);
        userMarkerRepository = mock(UserMarkerRepository.class);
        service = new ProblemTypeServiceImpl(problemTypeRepository, userMarkerRepository);
    }

    @Test
    void saveNewProblem_shouldSaveAndReturnEntity() {
        ProblemTypeDTO dto = new ProblemTypeDTO();
        dto.setCode("CODE_X");
        dto.setDescription("desc");

        ProblemTypeEntity entity = new ProblemTypeEntity();
        entity.setCode("CODE_X");
        entity.setDescription("desc");

        when(problemTypeRepository.save(any())).thenReturn(entity);

        ProblemTypeEntity result = service.saveNewProblem(dto);

        assertThat(result.getCode()).isEqualTo("CODE_X");
        assertThat(result.getDescription()).isEqualTo("desc");
        verify(problemTypeRepository).save(any());
    }

    @Test
    void getAllProblems_shouldReturnList() {
        ProblemTypeEntity e1 = new ProblemTypeEntity();
        ProblemTypeEntity e2 = new ProblemTypeEntity();

        when(problemTypeRepository.findAll()).thenReturn(List.of(e1, e2));

        List<ProblemTypeEntity> result = service.getAllProblems();

        assertThat(result).hasSize(2);
        verify(problemTypeRepository).findAll();
    }

    @Test
    void getProblemById_shouldReturnOptional() {
        ProblemTypeEntity entity = new ProblemTypeEntity();
        entity.setId(42);

        when(problemTypeRepository.findById(42)).thenReturn(Optional.of(entity));

        Optional<ProblemTypeEntity> result = service.getProblemById(42);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(42);
        verify(problemTypeRepository).findById(42);
    }

    @Test
    void deleteProblemById_shouldReturnNotFound_whenProblemNotExists() {
        when(problemTypeRepository.findById(99)).thenReturn(Optional.empty());

        ResponseDTO response = service.deleteProblemById(99);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).contains("не найдена");
    }

    @Test
    void deleteProblemById_shouldReturnError_whenProblemIsDefault() {
        ProblemTypeEntity problem = new ProblemTypeEntity();
        problem.setId(1);
        problem.setCode("DEFAULT");
        problem.setDefault(true);

        when(problemTypeRepository.findById(1)).thenReturn(Optional.of(problem));

        ResponseDTO response = service.deleteProblemById(1);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).contains("Нельзя удалить дефолтную");
    }

    @Test
    void deleteProblemById_shouldDeleteAndReturnSuccess() {
        ProblemTypeEntity problem = new ProblemTypeEntity();
        problem.setId(1);
        problem.setCode("TO_DELETE");
        problem.setDefault(false);

        ProblemTypeEntity defaultProblem = new ProblemTypeEntity();
        defaultProblem.setCode("DEFAULT");

        when(problemTypeRepository.findById(1)).thenReturn(Optional.of(problem));
        when(problemTypeRepository.findDefaultProblem()).thenReturn(defaultProblem);
        when(userMarkerRepository.updateProblemForMarkers("TO_DELETE", "DEFAULT")).thenReturn(5);

        ResponseDTO response = service.deleteProblemById(1);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).contains("Проблема удалена");

        verify(userMarkerRepository).updateProblemForMarkers("TO_DELETE", "DEFAULT");
        verify(problemTypeRepository).deleteById(1);
    }
}
