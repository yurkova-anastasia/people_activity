package com.ku.devices.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Accessors(chain = true)
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    @Column(name = "device_status_enum")
    private DeviceStatus deviceStatus;

    private String username;

    @Column(name = "inserted_date_at_utc")
    private LocalDateTime insertedDateAtUtc;

    @Column(name = "updated_date_at_utc")
    private LocalDateTime updatedDateAtUtc;

    @OneToMany(mappedBy="device", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DevicePings> devicePings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_type")
    private DeviceType deviceType;
}
