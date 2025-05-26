package com.example.collect_user_marker.service.impl;

import com.example.collect_user_marker.consumer.dto.PhotoAnalyseRespDTO;
import com.example.collect_user_marker.entity.*;
import com.example.collect_user_marker.exception.custom.*;
import com.example.collect_user_marker.feignClient.FeignClientPhotoAnalyseService;
import com.example.collect_user_marker.feignClient.FeignClientUserService;
import com.example.collect_user_marker.model.*;
import com.example.collect_user_marker.model.image.ImageDTO;
import com.example.collect_user_marker.producer.KafkaProducerService;
import com.example.collect_user_marker.producer.dto.PhotoAnalyseReqDTO;
import com.example.collect_user_marker.producer.dto.UpdateElementDTO;
import com.example.collect_user_marker.repository.ProblemTypeRepository;
import com.example.collect_user_marker.repository.StatusRepository;
import com.example.collect_user_marker.repository.UserMarkerRepository;
import com.example.collect_user_marker.util.JwtParserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserMarkerServiceImplTest {

    private UserMarkerRepository userMarkerRepository;
    private StatusRepository statusRepository;
    private ProblemTypeRepository problemTypeRepository;
    private FeignClientPhotoAnalyseService feignClientPhotoAnalyseService;
    private FeignClientUserService feignClientUserService;
    private KafkaProducerService kafkaProducerService;
    private JwtParserUtil jwtParserUtil;

    private UserMarkerServiceImpl service;

    @BeforeEach
    void setUp() {
        userMarkerRepository = mock(UserMarkerRepository.class);
        statusRepository = mock(StatusRepository.class);
        problemTypeRepository = mock(ProblemTypeRepository.class);
        feignClientPhotoAnalyseService = mock(FeignClientPhotoAnalyseService.class);
        feignClientUserService = mock(FeignClientUserService.class);
        kafkaProducerService = mock(KafkaProducerService.class);
        jwtParserUtil = mock(JwtParserUtil.class);

        service = new UserMarkerServiceImpl(userMarkerRepository, statusRepository, problemTypeRepository,
                feignClientPhotoAnalyseService, feignClientUserService, kafkaProducerService, jwtParserUtil);
    }

    @Test
    void saveNewReport_shouldSaveAndSendKafka() {
        UserMarkerDTO dto = new UserMarkerDTO();
        dto.setCoordinate(List.of(55.75, 37.61));

        DetailsDTO details = new DetailsDTO();
        details.setUserId(UUID.randomUUID());
        details.setComment("Some comment");
        details.setProblemAreaType("Борщевик");
        details.setImages(List.of(new ImageDTO(UUID.randomUUID(), UUID.randomUUID())));
        dto.setDetails(details);

        StatusEntity defaultStatus = new StatusEntity();
        defaultStatus.setCode("NEW");
        ProblemTypeEntity problem = new ProblemTypeEntity();
        problem.setCode("Борщевик");

        when(statusRepository.findDefaultStatus()).thenReturn(defaultStatus);
        when(problemTypeRepository.findByCode("Борщевик")).thenReturn(problem);
        when(userMarkerRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        UserMarkerEntity result = service.saveNewReport(dto, "Bearer token");

        assertThat(result.getStatus()).isEqualTo("NEW");
        verify(userMarkerRepository).save(any());
        verify(kafkaProducerService).sendUpdate(any(UpdateElementDTO.class));
        verify(kafkaProducerService).sendPhoto(any(PhotoAnalyseReqDTO.class));
    }

    @Test
    void saveNewReport_shouldThrow_ifCoordinatesNull() {
        UserMarkerDTO dto = new UserMarkerDTO();
        dto.setCoordinate(null);

        assertThatThrownBy(() -> service.saveNewReport(dto, "token"))
                .isInstanceOf(IncorrectDataException.class)
                .hasMessageContaining("Dont have X, Y");
    }

    @Test
    void getAllReports_shouldReturnPageForUserRole() {
        UUID userId = UUID.randomUUID();
        UserMarkerEntity marker = new UserMarkerEntity();
        Page<UserMarkerEntity> page = new PageImpl<>(List.of(marker));

        UserDTO mockUser = new UserDTO();
        mockUser.setId(userId);

        when(jwtParserUtil.extractRoleFromJwt("Bearer token")).thenReturn("user");
        when(jwtParserUtil.extractEmailFromJwt("Bearer token")).thenReturn("test@example.com");
        when(feignClientUserService.getUserByEmail("Bearer token", "test@example.com"))
                .thenReturn(mockUser);

        when(userMarkerRepository.findByUserId(any(), any(), eq(userId))).thenReturn(page);

        var result = service.getAllReports("Bearer token", 0, 10, null, null, null, "date", Sort.Direction.DESC);

        assertThat(result.getContent()).hasSize(1);
        verify(userMarkerRepository).findByUserId(any(), any(), eq(userId));
    }

    @Test
    void getReportById_shouldReturnEntity() {
        UUID id = UUID.randomUUID();
        UserMarkerEntity entity = new UserMarkerEntity();
        entity.setId(id);

        when(userMarkerRepository.findById(id)).thenReturn(Optional.of(entity));

        UserMarkerEntity result = service.getReportById(id);

        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    void getReportById_shouldThrow_ifNotFound() {
        UUID id = UUID.randomUUID();
        when(userMarkerRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getReportById(id))
                .isInstanceOf(ReportNotFoundException.class);
    }

    @Test
    void updateReport_shouldUpdateStatusAndSendKafka() {
        UUID markerId = UUID.randomUUID();
        OperatorDetailsDTO dto = new OperatorDetailsDTO();
        dto.setStatusCode("APPROVED");
        dto.setOperatorComment("All good");
        dto.setOperatorId(UUID.randomUUID());

        UserMarkerEntity marker = new UserMarkerEntity();
        marker.setId(markerId);

        StatusEntity mockStatus = new StatusEntity();
        mockStatus.setCode("APPROVED");

        when(userMarkerRepository.findById(markerId)).thenReturn(Optional.of(marker));
        when(statusRepository.findByCode("APPROVED")).thenReturn(mockStatus);
        when(userMarkerRepository.save(any())).thenReturn(marker);

        UserMarkerEntity result = service.updateReport(dto, markerId, "Bearer token");

        assertThat(result.getStatus()).isEqualTo("APPROVED");
        verify(kafkaProducerService).sendUpdate(any(UpdateElementDTO.class));
    }

    @Test
    void updatePhotoVerification_shouldUpdatePhotoPredictionsAndSave() {
        UUID markerId = UUID.randomUUID();
        PhotoAnalyseRespDTO dto = new PhotoAnalyseRespDTO();
        dto.setUserMarkerId(markerId);
        dto.setProtoPosition(0);
        dto.setPrediction(80);

        UserMarkerEntity entity = new UserMarkerEntity();
        entity.setId(markerId);
        entity.setPhotoPredictions(new ArrayList<>(Collections.singletonList(-1)));

        when(userMarkerRepository.findById(markerId)).thenReturn(Optional.of(entity));

        service.updatePhotoVerification(dto);

        assertThat(entity.getPhotoPredictions().get(0)).isEqualTo(80);
        verify(userMarkerRepository).save(entity);
    }

}
