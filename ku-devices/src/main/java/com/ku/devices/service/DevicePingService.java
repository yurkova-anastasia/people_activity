package com.ku.devices.service;

import com.ku.common.dto.DevicePingDto;
import com.ku.devices.repository.DevicePingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DevicePingService {

    private DevicePingRepository devicePingsRepository;

    @Autowired
    public void setDevicePingsRepository(DevicePingRepository devicePingsRepository) {
        this.devicePingsRepository = devicePingsRepository;
    }

    public void saveDevicePing(List<DevicePingDto> devicePingsDto) {
        devicePingsRepository.save(devicePingsDto);
        log.info("Device ping persisted {}", devicePingsDto);
    }
}
