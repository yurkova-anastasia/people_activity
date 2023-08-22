package com.ku.devices.service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DeviceCoordinatesDto;
import com.ku.devices.exception.DtoMappingException;
import com.ku.devices.service.DeviceCoordinatesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DeviceCoordinatesConsumer {

    private final ObjectMapper objectMapper;
    private final DeviceCoordinatesService coordinatesService;

    @Autowired
    public DeviceCoordinatesConsumer(ObjectMapper objectMapper, DeviceCoordinatesService coordinatesService) {
        this.objectMapper = objectMapper;
        this.coordinatesService = coordinatesService;
    }

    @KafkaListener(topics = "${topic.coordinates.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(List<String> messages) {
        List<DeviceCoordinatesDto> coordinatesDtos = new ArrayList<>();
        messages.forEach(
                message -> {
                    try {
                        var coordinatesDto = objectMapper.readValue(message, DeviceCoordinatesDto.class);
                        coordinatesDtos.add(coordinatesDto);
                    } catch (JsonProcessingException e) {
                        log.error("Failure during processing the following message: " + message);
                        throw new DtoMappingException("There was a problem when processing JSON content", e);
                    }
                });

        coordinatesService.saveDeviceCoordinates(coordinatesDtos);
    }
}