package com.ku.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class DevicePingDto implements Serializable {

    private Float weight;

    private Float height;

    private Integer pulse;

    private Float temperature;

    private Integer heartbeat;

    private Integer respiratoryRate;

    private Integer systolicPressure;

    private Integer diastolicPressure;

    private LocalDateTime insertedDateAtUtc;

    private Long deviceId;

    private Boolean active;
}
