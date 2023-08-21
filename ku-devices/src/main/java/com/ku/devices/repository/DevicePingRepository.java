package com.ku.devices.repository;

import com.ku.common.dto.DevicePingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class DevicePingRepository {

    public static final String SAVE = """
          INSERT INTO device_pings (weight, height, pulse, temperature, heartbeat,
                respiratory_rate, systolic_pressure, diastolic_pressure, inserted_date_at_utc, device_id, active)
          VALUES (:weight, :height, :pulse, :temperature, :heartbeat,
                :respiratoryRate, :systolicPressure, :diastolicPressure, :insertedDateAtUtc, :deviceId, :active)
    """;

    public static final String SELECT_FOR_UPDATE = """
         SELECT * FROM device_pings WHERE device_id IN (:deviceIds) FOR UPDATE
    """;

    public static final String UPDATE_ACTIVE = """
         UPDATE device_pings SET active = FALSE WHERE device_id IN (:deviceIds)
    """;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public void save(List<DevicePingDto> devicePingDto) {
        var groupedByDeviceId = groupingByDeviceId(devicePingDto);

        List<MapSqlParameterSource> batchParams = new ArrayList<>();

        groupedByDeviceId.values().forEach(pingDto -> batchParams.add(fillParameters(pingDto)));

        selectForUpdate(groupedByDeviceId);

        updateActiveField(groupedByDeviceId);

        saveBatchOfDevicePingDto(batchParams);
    }

    private void saveBatchOfDevicePingDto(List<MapSqlParameterSource> batchParams) {
        namedParameterJdbcTemplate.batchUpdate(SAVE, batchParams.toArray(MapSqlParameterSource[]::new));
    }

    private void updateActiveField(Map<Long, DevicePingDto> groupedByDeviceId) {
        namedParameterJdbcTemplate.update(
            UPDATE_ACTIVE,
            Collections.singletonMap("deviceIds", new ArrayList<>(groupedByDeviceId.keySet()))
        );
    }

    private void selectForUpdate(Map<Long, DevicePingDto> groupedByDeviceId) {
        namedParameterJdbcTemplate.query(
            SELECT_FOR_UPDATE,
            Collections.singletonMap("deviceIds", new ArrayList<>(groupedByDeviceId.keySet())),
            rs -> {});
    }

    private static Map<Long, DevicePingDto> groupingByDeviceId(List<DevicePingDto> devicePingDto) {
        return devicePingDto.stream()
                                .collect(Collectors.toMap(
                                    DevicePingDto::getDeviceId, Function.identity(),
                                    BinaryOperator.maxBy(
                                        Comparator.comparing(DevicePingDto::getInsertedDateAtUtc))
                                ));
    }

    private MapSqlParameterSource fillParameters(DevicePingDto devicePingDto) {
        return new MapSqlParameterSource()
                   .addValue("weight", devicePingDto.getWeight())
                   .addValue("height", devicePingDto.getHeight())
                   .addValue("pulse", devicePingDto.getPulse())
                   .addValue("temperature", devicePingDto.getTemperature())
                   .addValue("heartbeat", devicePingDto.getHeartbeat())
                   .addValue("respiratoryRate", devicePingDto.getRespiratoryRate())
                   .addValue("systolicPressure", devicePingDto.getSystolicPressure())
                   .addValue("diastolicPressure", devicePingDto.getDiastolicPressure())
                   .addValue("insertedDateAtUtc", devicePingDto.getInsertedDateAtUtc())
                   .addValue("deviceId", devicePingDto.getDeviceId())
                   .addValue("active", true);
    }
}
