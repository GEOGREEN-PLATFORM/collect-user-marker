package com.example.collect_user_marker.controller;

import com.example.collect_user_marker.entity.UserMarkerEntity;
import com.example.collect_user_marker.model.OperatorDetailsDTO;
import com.example.collect_user_marker.model.UserMarkerDTO;
import com.example.collect_user_marker.service.UserMarkerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserMarkerController.class)
@Import(UserMarkerControllerTest.MockConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class UserMarkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserMarkerService userMarkerService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UserMarkerService userMarkerService() {
            return Mockito.mock(UserMarkerService.class);
        }
    }

    @Test
    void saveNewReport_shouldReturnCreatedMarker() throws Exception {
        UserMarkerDTO dto = new UserMarkerDTO();
        dto.setCoordinate(List.of(55.75, 37.61)); // Пример координат

        UserMarkerEntity entity = new UserMarkerEntity();
        entity.setId(UUID.randomUUID());

        when(userMarkerService.saveNewReport(any(UserMarkerDTO.class), anyString())).thenReturn(entity);

        mockMvc.perform(post("/user-marker/report")
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        verify(userMarkerService).saveNewReport(any(UserMarkerDTO.class), anyString());
    }

    @Test
    void getAllReports_shouldReturnPage() throws Exception {
        UserMarkerEntity marker1 = new UserMarkerEntity();
        marker1.setId(UUID.randomUUID());

        UserMarkerEntity marker2 = new UserMarkerEntity();
        marker2.setId(UUID.randomUUID());

        Page<UserMarkerEntity> mockedPage = new PageImpl<>(List.of(marker1, marker2));

        when(userMarkerService.getAllReports(anyString(), anyInt(), anyInt(), nullable(String.class), nullable(Instant.class), nullable(Instant.class), anyString(), any(Sort.Direction.class))).thenReturn(mockedPage);

        mockMvc.perform(get("/user-marker/getAll")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.totalPages").value(1));
    }




    @Test
    void getReportById_shouldReturnMarker() throws Exception {
        UUID reportId = UUID.randomUUID();
        UserMarkerEntity entity = new UserMarkerEntity();
        entity.setId(reportId);

        when(userMarkerService.getReportById(reportId)).thenReturn(entity);

        mockMvc.perform(get("/user-marker/{reportId}", reportId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reportId.toString()));

        verify(userMarkerService).getReportById(reportId);
    }

    @Test
    void updateReport_shouldReturnUpdatedMarker() throws Exception {
        UUID reportId = UUID.randomUUID();
        OperatorDetailsDTO detailsDTO = new OperatorDetailsDTO();
        detailsDTO.setOperatorComment("Approved");

        UserMarkerEntity updatedEntity = new UserMarkerEntity();
        updatedEntity.setId(reportId);

        when(userMarkerService.updateReport(any(), eq(reportId), anyString()))
                .thenReturn(updatedEntity);

        mockMvc.perform(patch("/user-marker/{reportId}", reportId)
                        .header("Authorization", "Bearer token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detailsDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reportId.toString()));

        verify(userMarkerService).updateReport(any(), eq(reportId), anyString());
    }
}
