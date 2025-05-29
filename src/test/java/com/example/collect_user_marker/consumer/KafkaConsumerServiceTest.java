package com.example.collect_user_marker.consumer;

import com.example.collect_user_marker.consumer.dto.PhotoAnalyseRespDTO;
import com.example.collect_user_marker.service.UserMarkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class KafkaConsumerServiceTest {

    private UserMarkerService userMarkerService;
    private KafkaConsumerService consumerService;

    @BeforeEach
    void setUp() {
        userMarkerService = mock(UserMarkerService.class);
        consumerService = new KafkaConsumerService(userMarkerService);
        // вручную внедрим зависимость

    }

    @Test
    void listen_shouldCallUpdatePhotoVerification() {
        PhotoAnalyseRespDTO dto = new PhotoAnalyseRespDTO();
        dto.setUserMarkerId(java.util.UUID.randomUUID());
        dto.setProtoPosition(0);
        dto.setPrediction(85);

        consumerService.listen(dto);

        verify(userMarkerService).updatePhotoVerification(dto);
    }
}
