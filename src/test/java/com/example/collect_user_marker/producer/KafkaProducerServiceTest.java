package com.example.collect_user_marker.producer;

import com.example.collect_user_marker.producer.dto.PhotoAnalyseReqDTO;
import com.example.collect_user_marker.producer.dto.UpdateElementDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class KafkaProducerServiceTest {

    private KafkaTemplate<String, PhotoAnalyseReqDTO> kafkaTemplate;
    private KafkaTemplate<String, UpdateElementDTO> kafkaUpdateTemplate;
    private KafkaProducerService kafkaProducerService;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        kafkaUpdateTemplate = mock(KafkaTemplate.class);
        kafkaProducerService = new KafkaProducerService(kafkaTemplate, kafkaUpdateTemplate);
    }

    @Test
    @DisplayName("sendPhoto should send message to 'photo-analyse-req'")
    void testSendPhoto() {
        PhotoAnalyseReqDTO dto = new PhotoAnalyseReqDTO();
        kafkaProducerService.sendPhoto(dto);

        verify(kafkaTemplate, times(1)).send("photo-analyse-req", dto);
    }

    @Test
    @DisplayName("sendUpdate should send message to 'updateTopic'")
    void testSendUpdate() {
        UpdateElementDTO dto = new UpdateElementDTO();
        kafkaProducerService.sendUpdate(dto);

        verify(kafkaUpdateTemplate, times(1)).send("updateTopic", dto);
    }
}
