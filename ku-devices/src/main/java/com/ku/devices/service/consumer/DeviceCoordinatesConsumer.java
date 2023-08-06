package com.ku.devices.service.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DeviceCoordinatesSaveDto;
import com.ku.common.dto.DevicePingDto;
import com.ku.devices.service.DeviceCoordinatesService;
import com.ku.devices.service.DevicePingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeviceCoordinatesConsumer {

    private static final String TOPIC = "${topic.coordinates.name}";

    private final ObjectMapper objectMapper;
    private final DeviceCoordinatesService coordinatesService;

    @Autowired
    public DeviceCoordinatesConsumer(ObjectMapper objectMapper, DeviceCoordinatesService coordinatesService) {
        this.objectMapper = objectMapper;
        this.coordinatesService = coordinatesService;
    }

    @KafkaListener(topics = TOPIC)
    public void consumeMessage(String message) throws JsonProcessingException {
        var deviceCoordinatesSaveDto = objectMapper.readValue(message, DeviceCoordinatesSaveDto.class);
        coordinatesService.saveDeviceCoordinates(deviceCoordinatesSaveDto);
    }

}