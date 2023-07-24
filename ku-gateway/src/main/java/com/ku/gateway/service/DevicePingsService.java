package com.ku.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ku.common.dto.DevicePingsDto;
import com.ku.gateway.service.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DevicePingsService {

    private RestTemplate restTemplate;

    private Producer producer;

    public String addPing(DevicePingsDto ping) throws JsonProcessingException {
        return producer.sendMessage(ping);
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setProducer(Producer producer) {
        this.producer = producer;
    }
}
