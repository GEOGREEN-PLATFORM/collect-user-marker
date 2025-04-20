package com.example.collect_user_marker.producer;

import com.example.collect_user_marker.producer.dto.PhotoAnalyseReqDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, PhotoAnalyseReqDTO> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, PhotoAnalyseReqDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPhoto(PhotoAnalyseReqDTO photo) {
        kafkaTemplate.send("photo-analyse-req", photo);
    }
}
