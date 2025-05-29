package com.example.collect_user_marker.controller;

import com.example.collect_user_marker.entity.ProblemTypeEntity;
import com.example.collect_user_marker.model.ResponseDTO;
import com.example.collect_user_marker.model.problemType.ProblemTypeDTO;
import com.example.collect_user_marker.service.ProblemTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProblemTypeController.class)
@Import(ProblemTypeControllerTest.MockConfig.class)
//@WithMockUser(roles = "OPERATOR")
@AutoConfigureMockMvc(addFilters = false)
class ProblemTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProblemTypeService problemTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ProblemTypeService problemTypeService() {
            return Mockito.mock(ProblemTypeService.class);
        }
    }

    @Test
    void saveNewProblem_shouldReturnCreatedProblem() throws Exception {
        ProblemTypeDTO dto = new ProblemTypeDTO();
        dto.setCode("CODE1");

        ProblemTypeEntity entity = new ProblemTypeEntity();
        entity.setCode("CODE1");

        when(problemTypeService.saveNewProblem(any(ProblemTypeDTO.class))).thenReturn(entity);

        mockMvc.perform(post("/user-marker/problemType/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("CODE1"));

        verify(problemTypeService).saveNewProblem(any(ProblemTypeDTO.class));
    }

    @Test
    void getAllProblems_shouldReturnList() throws Exception {
        ProblemTypeEntity entity1 = new ProblemTypeEntity();
        entity1.setId(1);
        entity1.setCode("A");

        ProblemTypeEntity entity2 = new ProblemTypeEntity();
        entity2.setId(2);
        entity2.setCode("B");

        when(problemTypeService.getAllProblems()).thenReturn(List.of(entity1, entity2));

        mockMvc.perform(get("/user-marker/problemType/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(problemTypeService).getAllProblems();
    }

    @Test
    void getProblemById_shouldReturnOneProblem() throws Exception {
        int id = 1;
        ProblemTypeEntity entity = new ProblemTypeEntity();
        entity.setId(id);
        entity.setCode("PROB1");

        when(problemTypeService.getProblemById(id)).thenReturn(Optional.of(entity));

        mockMvc.perform(get("/user-marker/problemType/get/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("PROB1"));

        verify(problemTypeService).getProblemById(id);
    }

    @Test
    void deleteProblemById_shouldReturnResponse() throws Exception {
        int id = 1;
        ResponseDTO response = new ResponseDTO(HttpStatus.OK, "Проблема удалена!");

        when(problemTypeService.deleteProblemById(id)).thenReturn(response);

        mockMvc.perform(delete("/user-marker/problemType/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Проблема удалена!"));

        verify(problemTypeService).deleteProblemById(id);
    }
}
