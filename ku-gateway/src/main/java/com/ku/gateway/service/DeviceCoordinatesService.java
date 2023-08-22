package com.ku.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ku.common.dto.DeviceCoordinatesDto;
import com.ku.gateway.service.producer.DeviceCoordinatesProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceCoordinatesService {

    private DeviceCoordinatesProducer producer;

    public void save(DeviceCoordinatesDto coordinates) throws JsonProcessingException {
        producer.sendMessage(coordinates);
    }

    @Autowired
    public void setProducer(DeviceCoordinatesProducer producer) {
        this.producer = producer;
    }
}
