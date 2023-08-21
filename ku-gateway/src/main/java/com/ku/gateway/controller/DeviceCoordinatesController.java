package com.ku.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ku.common.dto.DeviceCoordinatesDto;
import com.ku.gateway.service.DeviceCoordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coordinates")
public class DeviceCoordinatesController {

    private DeviceCoordinatesService deviceCoordinatesService;

    @PostMapping
    public void save(@RequestBody DeviceCoordinatesDto coordinates) throws JsonProcessingException {
        deviceCoordinatesService.save(coordinates);
    }

    @Autowired
    public void setDevicePingsService(DeviceCoordinatesService deviceCoordinatesService) {
        this.deviceCoordinatesService = deviceCoordinatesService;
    }
}
