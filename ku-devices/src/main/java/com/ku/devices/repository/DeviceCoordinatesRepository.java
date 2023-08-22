package com.ku.devices.repository;

import com.ku.common.dto.DeviceCoordinatesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class DeviceCoordinatesRepository {

    public static final String SELECT_FOR_UPDATE = """
                 SELECT * FROM device_coordinates WHERE device_id IN (:deviceIds) FOR UPDATE
            """;

    public static final String UPDATE_ACTIVE = """
                 UPDATE device_coordinates SET active = FALSE WHERE device_id IN (:deviceIds)
            """;

    public static final String SAVE = """
              INSERT INTO device_coordinates (latitude, longitude, active, device_id)
              VALUES (:latitude, :longitude, :active, :deviceId)
            """;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public void save(List<DeviceCoordinatesDto> coordinatesSaveDtos) {
        Set<Long> deviceIds = coordinatesSaveDtos.stream()
                .map(DeviceCoordinatesDto::getDeviceId)
                .collect(Collectors.toSet());

        namedParameterJdbcTemplate.queryForList(
                SELECT_FOR_UPDATE,
                new MapSqlParameterSource().addValue("deviceIds", deviceIds)
        );

        namedParameterJdbcTemplate.update(
                UPDATE_ACTIVE,
                new MapSqlParameterSource().addValue("deviceIds", deviceIds)
        );

        MapSqlParameterSource[] paramsForBulkInsert = coordinatesSaveDtos.stream()
                .map(this::fillParametersForSaveQuery).toArray(MapSqlParameterSource[]::new);

        namedParameterJdbcTemplate.batchUpdate(SAVE, paramsForBulkInsert);
    }

    private MapSqlParameterSource fillParametersForSaveQuery(DeviceCoordinatesDto coordinatesSaveDto) {
        return new MapSqlParameterSource()
                .addValue("latitude", coordinatesSaveDto.getLatitude())
                .addValue("longitude", coordinatesSaveDto.getLongitude())
                .addValue("active", true)
                .addValue("deviceId", coordinatesSaveDto.getDeviceId());
    }

    @Autowired
    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
}
