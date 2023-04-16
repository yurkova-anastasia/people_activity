package com.ku.devices.controller;

import com.ku.common.dto.DeviceDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @GetMapping(produces = "application/json")
    public DeviceDto find() {
        return new DeviceDto()
                .setModel("iphone");
    }
}
