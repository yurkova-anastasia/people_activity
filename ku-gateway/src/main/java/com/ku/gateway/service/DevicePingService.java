package com.ku.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ku.common.dto.DevicePingDto;
import com.ku.gateway.service.producer.DevicePingProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DevicePingService {

    private RestTemplate restTemplate;

    private DevicePingProducer producer;

    public void save(DevicePingDto ping) throws JsonProcessingException {
        producer.sendMessage(ping);
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setProducer(DevicePingProducer producer) {
        this.producer = producer;
    }
}
