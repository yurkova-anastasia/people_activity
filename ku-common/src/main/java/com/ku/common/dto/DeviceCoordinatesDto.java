package com.ku.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DeviceCoordinatesDto {

    private double latitude;
    private double longitude;
    private Long deviceId;
}
