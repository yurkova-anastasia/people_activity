package com.ku.devices.service;

import com.ku.common.dto.DeviceCoordinatesDto;
import com.ku.devices.repository.DeviceCoordinatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceCoordinatesService {

    private DeviceCoordinatesRepository coordinatesRepository;

    public void saveDeviceCoordinates(List<DeviceCoordinatesDto> coordinatesSaveDtos) {
        coordinatesRepository.save(coordinatesSaveDtos);
    }

    @Autowired
    public void setDeviceCoordinatesRepository(DeviceCoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
    }
}
