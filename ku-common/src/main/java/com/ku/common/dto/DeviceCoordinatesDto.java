package com.ku.common.dto;

import lombok.Data;

@Data
public class DeviceCoordinatesDto {

    private double latitude;
    private double longitude;
    private Long deviceId;
}
