package com.ku.devices.service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DevicePingDto;
import com.ku.devices.exception.DtoMappingException;
import com.ku.devices.service.DevicePingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    @KafkaListener(topics = "${topic.pings.name}", groupId = "device")
    public void consumeMessage(List<String> messages) {
        List<DevicePingDto> pingDto = new ArrayList<>();
        messages.forEach(
            message -> {
                try {
                    var devicePingDto = objectMapper.readValue(message, DevicePingDto.class);
                    pingDto.add(devicePingDto);
                } catch (JsonProcessingException e) {
                    log.info("There was a problem when processing JSON content", e);
                    throw new DtoMappingException("There was a problem when processing JSON content", e);
                }
            });
        devicePingService.saveDevicePing(pingDto);
    }
}