package com.ku.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ku.common.dto.DevicePingsDto;
import com.ku.gateway.service.DevicePingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device_pings")
public class DevicePingsController {

    private DevicePingsService devicePingsService;

    @PostMapping
    public String aggPing(@RequestBody DevicePingsDto ping) throws JsonProcessingException {
        return devicePingsService.addPing(ping);
    }

    @Autowired
    public void setDevicePingsService(DevicePingsService devicePingsService) {
        this.devicePingsService = devicePingsService;
    }
}
