package com.ku.devices.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "device-pings")
public class DevicePings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float weight;

    private Float height;

    private Integer pulse;

    private Float temperature;

    private Integer heartbeat;

    @Column(name = "respiratory_rate")
    private Integer respiratoryRate;

    @Column(name = "systolic_pressure")
    private Integer systolicPressure;

    @Column(name = "diastolic_pressure")
    private Integer diastolicPressure;

    @Column(name = "inserted_date_at_utc")
    private LocalDateTime insertedDateAtUtc;

    @Column(name = "updated_date_at_utc")
    private LocalDateTime updatedDateAtUtc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;
}
