package com.ku.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ku.common.dto.DevicePingDto;
import com.ku.gateway.service.DevicePingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devicePings")
public class DevicePingController {

    private DevicePingService devicePingService;

    @PostMapping
    public ResponseEntity<String> save(@RequestBody DevicePingDto devicePingDto)
            throws JsonProcessingException {
        devicePingService.save(devicePingDto);
        return ResponseEntity.ok("Device ping processed successfully");
    }

    @Autowired
    public void setDevicePingsService(DevicePingService devicePingsService) {
        this.devicePingService = devicePingsService;
    }
}
