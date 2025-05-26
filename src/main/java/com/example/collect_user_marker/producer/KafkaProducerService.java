package com.example.collect_user_marker.producer;

import com.example.collect_user_marker.producer.dto.PhotoAnalyseReqDTO;
import com.example.collect_user_marker.producer.dto.UpdateElementDTO;
import com.example.collect_user_marker.service.impl.UserMarkerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, PhotoAnalyseReqDTO> kafkaTemplate;
    private final KafkaTemplate<String, UpdateElementDTO> kafkaUpdateTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);


    @Value("${kafka.update.topic}")
    private String updateTopic;

    public KafkaProducerService(KafkaTemplate<String, PhotoAnalyseReqDTO> kafkaTemplate,
                                KafkaTemplate<String, UpdateElementDTO> kafkaUpdateTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaUpdateTemplate = kafkaUpdateTemplate;
    }

    public void sendPhoto(PhotoAnalyseReqDTO photo) {
        kafkaTemplate.send("photo-analyse-req", photo);
    }

    public void sendUpdate(UpdateElementDTO update) {
        logger.info("Отправляю обновление в кафку: {}", update);
        kafkaUpdateTemplate.send(updateTopic, update);
    }
}
