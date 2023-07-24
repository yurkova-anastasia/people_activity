package com.ku.devices.repository;


import com.ku.devices.entity.DevicePings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class DevicePingsRepository {

    public static final String SAVE =
        "INSERT INTO device_pings (weight, height, pulse, temperature, heartbeat, respiratory_rate, systolic_pressure, diastolic_pressure, inserted_date_at_utc) " +
         "VALUES (:weight, :height, :pulse, :temperature, :heartbeat, :respiratoryRate, :systolicPressure, :diastolicPressure, :insertedDateAtUtc)";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public DevicePings save(DevicePings devicePings) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SAVE, fillParameters(devicePings), keyHolder, new String[]{"id"});
        return devicePings.setId((Long) keyHolder.getKey());
    }

    private MapSqlParameterSource fillParameters(DevicePings devicePings) {
        return new MapSqlParameterSource()
                   .addValue("weight", devicePings.getWeight())
                   .addValue("height", devicePings.getHeight())
                   .addValue("pulse", devicePings.getPulse())
                   .addValue("temperature", devicePings.getTemperature())
                   .addValue("heartbeat", devicePings.getHeartbeat())
                   .addValue("respiratoryRate", devicePings.getRespiratoryRate())
                   .addValue("systolicPressure", devicePings.getSystolicPressure())
                   .addValue("diastolicPressure", devicePings.getDiastolicPressure())
                   .addValue("insertedDateAtUtc", devicePings.getInsertedDateAtUtc());
    }
}
