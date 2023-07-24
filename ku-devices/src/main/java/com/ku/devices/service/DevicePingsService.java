package com.ku.devices.service;

import com.ku.devices.entity.DevicePings;
import com.ku.devices.repository.DevicePingsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DevicePingsService {

    private DevicePingsRepository devicePingsRepository;

    @Autowired
    public void setDevicePingsRepository(DevicePingsRepository devicePingsRepository) {
        this.devicePingsRepository = devicePingsRepository;
    }


    public void persistDevicePings(DevicePings devicePings) {
        DevicePings pings = devicePingsRepository.save(devicePings);
        log.info("Device ping persisted {}", pings);
    }
}
