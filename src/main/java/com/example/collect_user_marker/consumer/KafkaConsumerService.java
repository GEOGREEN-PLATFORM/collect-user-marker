package com.example.collect_user_marker.consumer;

import com.example.collect_user_marker.consumer.dto.PhotoAnalyseRespDTO;
import com.example.collect_user_marker.service.UserMarkerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Data
public class KafkaConsumerService {

    @Autowired
    private final UserMarkerService userMarkerService;

    @KafkaListener(topics = "photo-analyse-resp", groupId = "collect-markers-group",
            containerFactory = "photoAnalyseRespKafkaListenerContainerFactory")
    public void listen(PhotoAnalyseRespDTO photo) {
        userMarkerService.updatePhotoVerification(photo);
    }
}
