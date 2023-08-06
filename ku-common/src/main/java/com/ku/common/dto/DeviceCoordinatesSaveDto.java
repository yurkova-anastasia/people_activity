package com.ku.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class DeviceCoordinatesSaveDto {

    private BigDecimal latitude;
    private BigDecimal longitude;
    private Long deviceId;
}
