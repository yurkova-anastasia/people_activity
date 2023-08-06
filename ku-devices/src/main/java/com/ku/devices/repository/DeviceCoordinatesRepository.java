package com.ku.devices.repository;

import com.ku.common.dto.DeviceCoordinatesDto;
import com.ku.common.dto.DeviceCoordinatesSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceCoordinatesRepository {

    public static final String FIND_BY_ID = "SELECT * FROM device_coordinates WHERE id = :id";
    public static final String FIND_ALL_BY_DEVICE_ID = """
          SELECT * FROM device_coordinates
          WHERE device_id = :deviceId
          LIMIT :limit OFFSET :offset
        """;

    public static final String SAVE = """
          INSERT INTO device_coordinates (latitude, longitude, active, deviceId)
          VALUES (:latitude, :longitude, :active, :deviceId)
        """;

    public static final String UPDATING_AFTER_SAVE = """
          UPDATE device_coordinates
          SET active = false
          WHERE device_id = :deviceId
        """;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public DeviceCoordinatesDto findById(Long id) {
        return namedParameterJdbcTemplate.queryForObject(
                FIND_BY_ID,
                new MapSqlParameterSource().addValue("id", id),
                DeviceCoordinatesDto.class
        );
    }

    public List<DeviceCoordinatesDto> findAllByDeviceId(Long deviceId, Long limit, Long offset) {
        return namedParameterJdbcTemplate.queryForList(
                FIND_ALL_BY_DEVICE_ID,
                fillParametersForFindByDeviceIdQuery(deviceId, limit, offset),
                DeviceCoordinatesDto.class
        );
    }

    private MapSqlParameterSource fillParametersForFindByDeviceIdQuery(Long deviceId, Long limit, Long offset) {
        return new MapSqlParameterSource()
                .addValue("deviceId", deviceId)
                .addValue("limit", limit)
                .addValue("offset", offset);
    }

    public void save(DeviceCoordinatesSaveDto coordinatesSaveDto) {
        namedParameterJdbcTemplate.update(SAVE, fillParametersForSaveQuery(coordinatesSaveDto));
        namedParameterJdbcTemplate.update(
                UPDATING_AFTER_SAVE,
                new MapSqlParameterSource().addValue("deviceId", coordinatesSaveDto.getDeviceId()));
    }

    private MapSqlParameterSource fillParametersForSaveQuery(DeviceCoordinatesSaveDto coordinatesSaveDto) {
        return new MapSqlParameterSource()
                .addValue("latitude", coordinatesSaveDto.getLatitude())
                .addValue("longitude", coordinatesSaveDto.getLongitude())
                .addValue("active", true)
                .addValue("deviceId", coordinatesSaveDto.getDeviceId());
    }
}
