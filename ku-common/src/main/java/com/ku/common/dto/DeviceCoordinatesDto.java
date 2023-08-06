package com.ku.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class DeviceCoordinatesDto {

    private Long id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private boolean active;
    private Long deviceId;
}
