package com.ku.devices.service.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DevicePingDto;
import com.ku.devices.service.DevicePingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DevicePingPConsumer {

    private static final String topic = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final DevicePingsService devicePingsService;

    @Autowired
    public DevicePingPConsumer(ObjectMapper objectMapper, DevicePingsService devicePingsService) {
        this.objectMapper = objectMapper;
        this.devicePingsService = devicePingsService;
    }

    @KafkaListener(topics = topic)
    public void consumeMessage(String message) throws JsonProcessingException {
        var devicePingsDto = objectMapper.readValue(message, DevicePingDto.class);
        devicePingsService.persistDevicePings(devicePingsDto);
    }

}