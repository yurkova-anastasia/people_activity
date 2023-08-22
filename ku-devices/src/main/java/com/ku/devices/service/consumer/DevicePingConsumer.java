package com.ku.devices.service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DevicePingDto;
import com.ku.devices.service.DevicePingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DevicePingConsumer {

    private final ObjectMapper objectMapper;
    private final DevicePingService devicePingService;

    @Autowired
    public DevicePingConsumer(ObjectMapper objectMapper, DevicePingService devicePingService) {
        this.objectMapper = objectMapper;
        this.devicePingService = devicePingService;
    }

    @KafkaListener(topics = "${topic.pings.name}")
    public void consumeMessage(String message) throws JsonProcessingException {
        var devicePingDto = objectMapper.readValue(message, DevicePingDto.class);
        devicePingService.saveDevicePing(devicePingDto);
    }
}