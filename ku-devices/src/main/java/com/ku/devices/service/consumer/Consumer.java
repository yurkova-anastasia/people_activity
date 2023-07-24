package com.ku.devices.service.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DevicePingsDto;
import com.ku.devices.entity.DevicePings;
import com.ku.devices.service.DevicePingsService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    private static final String orderTopic = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final DevicePingsService devicePingsService;

    @Autowired
    public Consumer(ObjectMapper objectMapper, ModelMapper modelMapper, DevicePingsService devicePingsService) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.devicePingsService = devicePingsService;
    }

    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);

        DevicePingsDto devicePingsDto = objectMapper.readValue(message, DevicePingsDto.class);
        DevicePings devicePings = modelMapper.map(devicePingsDto, DevicePings.class);

        devicePingsService.persistDevicePings(devicePings);
    }

}