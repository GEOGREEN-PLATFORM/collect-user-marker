package com.example.collect_user_marker.producer;

import com.example.collect_user_marker.producer.dto.PhotoAnalyseReqDTO;
import com.example.collect_user_marker.producer.dto.UpdateElementDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, PhotoAnalyseReqDTO> kafkaTemplate;
    private final KafkaTemplate<String, UpdateElementDTO> kafkaUpdateTemplate;

    public KafkaProducerService(KafkaTemplate<String, PhotoAnalyseReqDTO> kafkaTemplate,
                                KafkaTemplate<String, UpdateElementDTO> kafkaUpdateTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaUpdateTemplate = kafkaUpdateTemplate;
    }

    public void sendPhoto(PhotoAnalyseReqDTO photo) {
        kafkaTemplate.send("photo-analyse-req", photo);
    }

    public void sendUpdate(UpdateElementDTO update) {
        kafkaUpdateTemplate.send("update-element", update);
    }
}
