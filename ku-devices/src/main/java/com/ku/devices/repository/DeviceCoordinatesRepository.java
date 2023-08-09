package com.ku.devices.repository;

import com.ku.common.dto.DeviceCoordinatesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DeviceCoordinatesRepository {

    public static final String SAVE = """
              INSERT INTO device_coordinates (latitude, longitude, active, deviceId)
              VALUES (:latitude, :longitude, :active, :deviceId)
            """;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void save(List<DeviceCoordinatesDto> coordinatesSaveDtos) {
        coordinatesSaveDtos.forEach(saveDto -> namedParameterJdbcTemplate.update(
                SAVE,
                fillParametersForSaveQuery(saveDto))
        );
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
