package com.ku.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ku.common.dto.DevicePingDto;
import com.ku.gateway.service.DevicePingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devicePing")
public class DevicePingController {

    private DevicePingService devicePingsService;

    @PostMapping
    public void save(@RequestBody DevicePingDto ping) throws JsonProcessingException {
        devicePingsService.save(ping);
    }

    @Autowired
    public void setDevicePingsService(DevicePingService devicePingsService) {
        this.devicePingsService = devicePingsService;
    }
}
