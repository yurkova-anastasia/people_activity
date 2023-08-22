package com.ku.gateway.service.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DeviceCoordinatesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeviceCoordinatesProducer {

    @Value("${topic.coordinates.name}")
    private String topic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public DeviceCoordinatesProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(DeviceCoordinatesDto coordinates) throws JsonProcessingException {
        var message = objectMapper.writeValueAsString(coordinates);
        kafkaTemplate.send(topic, message);
    }
}
