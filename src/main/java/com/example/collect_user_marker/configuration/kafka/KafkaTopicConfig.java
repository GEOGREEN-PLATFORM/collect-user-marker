package com.example.collect_user_marker.configuration.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.update.topic}")
    private String updateTopic;

    @Bean
    public NewTopic photoAnalyseRequestTopic() {
        return TopicBuilder.name("photo-analyse-req")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic photoAnalyseResponseTopic() {
        return TopicBuilder.name("photo-analyse-resp")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic updateElementTopic() {
        return TopicBuilder.name(updateTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
