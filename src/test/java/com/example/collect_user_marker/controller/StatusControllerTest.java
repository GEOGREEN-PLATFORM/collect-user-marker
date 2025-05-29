package com.example.collect_user_marker.controller;

import com.example.collect_user_marker.entity.StatusEntity;
import com.example.collect_user_marker.model.ResponseDTO;
import com.example.collect_user_marker.model.status.StatusDTO;
import com.example.collect_user_marker.service.StatusService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(StatusController.class)
@Import(StatusControllerTest.MockConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class StatusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StatusService statusService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public StatusService statusService() {
            return Mockito.mock(StatusService.class);
        }
    }

    @Test
    void saveNewStatus_shouldReturnCreatedStatus() throws Exception {
        StatusDTO dto = new StatusDTO();
        dto.setCode("STATUS1");

        StatusEntity entity = new StatusEntity();
        entity.setCode("STATUS1");

        when(statusService.saveNewStatus(any(StatusDTO.class))).thenReturn(entity);

        mockMvc.perform(post("/user-marker/status/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("STATUS1"));

        verify(statusService).saveNewStatus(any(StatusDTO.class));
    }

    @Test
    void getAllStatuses_shouldReturnList() throws Exception {
        StatusEntity entity1 = new StatusEntity();
        entity1.setId(1);
        entity1.setCode("A");

        StatusEntity entity2 = new StatusEntity();
        entity2.setId(2);
        entity2.setCode("B");

        when(statusService.getAllStatuses()).thenReturn(List.of(entity1, entity2));

        mockMvc.perform(get("/user-marker/status/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(statusService).getAllStatuses();
    }

    @Test
    void getStatusById_shouldReturnOneStatus() throws Exception {
        int id = 1;
        StatusEntity entity = new StatusEntity();
        entity.setId(id);
        entity.setCode("STAT1");

        when(statusService.findStatusById(id)).thenReturn(Optional.of(entity));

        mockMvc.perform(get("/user-marker/status/get/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("STAT1"));

        verify(statusService).findStatusById(id);
    }

    @Test
    void deleteStatus_shouldReturnResponse() throws Exception {
        int id = 1;
        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Статус удалён!");

        when(statusService.deleteStatus(id)).thenReturn(response);

        mockMvc.perform(delete("/user-marker/status/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Статус удалён!"));

        verify(statusService).deleteStatus(id);
    }
}
