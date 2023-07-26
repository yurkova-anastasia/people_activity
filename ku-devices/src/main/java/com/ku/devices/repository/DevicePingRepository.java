package com.ku.devices.repository;


import com.ku.common.dto.DevicePingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DevicePingRepository {

    public static final String SAVE = """
          INSERT INTO device_pings (weight, height, pulse, temperature, heartbeat,
                respiratory_rate, systolic_pressure, diastolic_pressure, inserted_date_at_utc)
          VALUES (:weight, :height, :pulse, :temperature, :heartbeat,
           :respiratoryRate, :systolicPressure, :diastolicPressure, :insertedDateAtUtc)
        """;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(DevicePingDto devicePingsDto) {
        namedParameterJdbcTemplate.update(SAVE, fillParameters(devicePingsDto));;
    }

    private MapSqlParameterSource fillParameters(DevicePingDto devicePingsDto) {
        return new MapSqlParameterSource()
                   .addValue("weight", devicePingsDto.getWeight())
                   .addValue("height", devicePingsDto.getHeight())
                   .addValue("pulse", devicePingsDto.getPulse())
                   .addValue("temperature", devicePingsDto.getTemperature())
                   .addValue("heartbeat", devicePingsDto.getHeartbeat())
                   .addValue("respiratoryRate", devicePingsDto.getRespiratoryRate())
                   .addValue("systolicPressure", devicePingsDto.getSystolicPressure())
                   .addValue("diastolicPressure", devicePingsDto.getDiastolicPressure())
                   .addValue("insertedDateAtUtc", devicePingsDto.getInsertedDateAtUtc());
    }
}
