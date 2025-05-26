package com.example.collect_user_marker.service.impl;

import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.model.ResponseDTO;
import com.example.collect_user_marker.model.status.StatusDTO;
import com.example.collect_user_marker.repository.StatusRepository;
import com.example.collect_user_marker.repository.UserMarkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StatusServiceImplTest {

    private StatusRepository statusRepository;
    private UserMarkerRepository userMarkerRepository;
    private StatusServiceImpl service;

    @BeforeEach
    void setUp() {
        statusRepository = mock(StatusRepository.class);
        userMarkerRepository = mock(UserMarkerRepository.class);
        service = new StatusServiceImpl(statusRepository, userMarkerRepository);
    }

    @Test
    void saveNewStatus_shouldSaveAndReturnEntity() {
        StatusDTO dto = new StatusDTO();
        dto.setCode("STAT_X");
        dto.setDescription("test description");

        StatusEntity entity = new StatusEntity();
        entity.setCode("STAT_X");
        entity.setDescription("test description");

        when(statusRepository.save(any())).thenReturn(entity);

        StatusEntity result = service.saveNewStatus(dto);

        assertThat(result.getCode()).isEqualTo("STAT_X");
        assertThat(result.getDescription()).isEqualTo("test description");
        verify(statusRepository).save(any());
    }

    @Test
    void getAllStatuses_shouldReturnList() {
        StatusEntity s1 = new StatusEntity();
        StatusEntity s2 = new StatusEntity();

        when(statusRepository.findAll()).thenReturn(List.of(s1, s2));

        List<StatusEntity> result = service.getAllStatuses();

        assertThat(result).hasSize(2);
        verify(statusRepository).findAll();
    }

    @Test
    void findStatusById_shouldReturnOptional() {
        StatusEntity entity = new StatusEntity();
        entity.setId(101);

        when(statusRepository.findById(101)).thenReturn(Optional.of(entity));

        Optional<StatusEntity> result = service.findStatusById(101);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(101);
        verify(statusRepository).findById(101);
    }

    @Test
    void deleteStatus_shouldReturnNotFound_whenStatusNotExists() {
        when(statusRepository.findById(999)).thenReturn(Optional.empty());

        ResponseDTO response = service.deleteStatus(999);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).contains("не найден");
    }

    @Test
    void deleteStatus_shouldReturnError_whenStatusIsDefault() {
        StatusEntity status = new StatusEntity();
        status.setId(1);
        status.setCode("DEFAULT");
        status.setDefault(true);

        when(statusRepository.findById(1)).thenReturn(Optional.of(status));

        ResponseDTO response = service.deleteStatus(1);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getMessage()).contains("Нельзя удалить дефолтный");
    }

    @Test
    void deleteStatus_shouldDeleteAndReturnSuccess() {
        StatusEntity status = new StatusEntity();
        status.setId(1);
        status.setCode("TO_DELETE");
        status.setDefault(false);

        StatusEntity defaultStatus = new StatusEntity();
        defaultStatus.setCode("DEFAULT");

        when(statusRepository.findById(1)).thenReturn(Optional.of(status));
        when(statusRepository.findDefaultStatus()).thenReturn(defaultStatus);
        when(userMarkerRepository.updateStatusForMarkers("TO_DELETE", "DEFAULT")).thenReturn(3);

        ResponseDTO response = service.deleteStatus(1);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).contains("удалён");

        verify(userMarkerRepository).updateStatusForMarkers("TO_DELETE", "DEFAULT");
        verify(statusRepository).deleteById(1);
    }
}
