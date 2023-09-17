package com.ku.devices.controller;

import com.ku.common.dto.DevicePingDto;
import com.ku.devices.service.DevicePingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/devicePings")
public class DevicePingController {

    private DevicePingService devicePingService;

    @GetMapping
    public List<DevicePingDto> findActive() {
        return devicePingService.findActive();
    }

    @Autowired
    public void setDevicePingsService(DevicePingService devicePingsService) {
        this.devicePingService = devicePingsService;
    }

}
