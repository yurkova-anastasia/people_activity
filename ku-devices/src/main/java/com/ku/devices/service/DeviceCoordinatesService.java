package com.ku.devices.service;

import com.ku.common.dto.DeviceCoordinatesSaveDto;
import com.ku.devices.repository.DeviceCoordinatesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeviceCoordinatesService {

    private DeviceCoordinatesRepository coordinatesRepository;

    @Autowired
    public void setDeviceCoordinatesRepository(DeviceCoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
    }


    public void saveDeviceCoordinates(DeviceCoordinatesSaveDto coordinatesSaveDto) {
        coordinatesRepository.save(coordinatesSaveDto);
        log.info("Device coordinates persisted {}", coordinatesSaveDto);
    }
}
