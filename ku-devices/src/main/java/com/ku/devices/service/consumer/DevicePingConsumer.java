package com.ku.devices.service.consumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DevicePingDto;
import com.ku.devices.exception.ConsumerException;
import com.ku.devices.service.DevicePingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DevicePingConsumer {

    private static final String topic = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final DevicePingService devicePingService;

    @Autowired
    public DevicePingConsumer(ObjectMapper objectMapper, DevicePingService devicePingService) {
        this.objectMapper = objectMapper;
        this.devicePingService = devicePingService;
    }

    @KafkaListener(topics = topic)
    public void consumeMessage(List<String> messages) {
        messages.forEach(
            message -> {
                try {
                    var devicePingDto = objectMapper.readValue(message, DevicePingDto.class);
                    devicePingService.saveDevicePing(devicePingDto);
                } catch (JsonProcessingException e) {
                    throw new ConsumerException("There was a problem when processing JSON content", e);
                }
            });
    }
}