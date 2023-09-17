package com.ku.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ku.common.dto.DevicePingDto;
import com.ku.gateway.util.DevicePingProcessor;
import com.ku.gateway.service.DevicePingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devicePing")
public class DevicePingController {

    private DevicePingService devicePingService;

    private DevicePingProcessor devicePingProcessor;

    @PostMapping
    public void save(@RequestBody DevicePingDto ping) throws JsonProcessingException {
        devicePingService.save(ping);
    }

    @PostMapping("/cache")
    public ResponseEntity<String> receiveDevicePing(@RequestBody DevicePingDto devicePingDto)
            throws JsonProcessingException {
        devicePingProcessor.processDevicePing(devicePingDto);
        return ResponseEntity.ok("Device ping processed successfully");
    }

    @Autowired
    public void setDevicePingsService(DevicePingService devicePingsService) {
        this.devicePingService = devicePingsService;
    }

    @Autowired
    public void setDevicePingProcessor(DevicePingProcessor devicePingProcessor) {
        this.devicePingProcessor = devicePingProcessor;
    }
}
