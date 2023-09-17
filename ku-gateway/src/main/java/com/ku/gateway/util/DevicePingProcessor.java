package com.ku.gateway.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DevicePingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DevicePingProcessor {
    @Value("${normalRanges.weight.min}")
    private float minWeight;

    @Value("${normalRanges.weight.max}")
    private float maxWeight;

    @Value("${normalRanges.height.min}")
    private float minHeight;

    @Value("${normalRanges.height.max}")
    private float maxHeight;

    @Value("${normalRanges.pulse.min}")
    private int minPulse;

    @Value("${normalRanges.pulse.max}")
    private int maxPulse;

    @Value("${normalRanges.temperature.min}")
    private float minTemperature;

    @Value("${normalRanges.temperature.max}")
    private float maxTemperature;

    @Value("${normalRanges.heartbeat.min}")
    private int minHeartbeat;

    @Value("${normalRanges.heartbeat.max}")
    private int maxHeartbeat;

    @Value("${normalRanges.respiratoryRate.min}")
    private int minRespiratoryRate;

    @Value("${normalRanges.respiratoryRate.max}")
    private int maxRespiratoryRate;

    @Value("${normalRanges.systolicPressure.min}")
    private int minSystolicPressure;

    @Value("${normalRanges.systolicPressure.max}")
    private int maxSystolicPressure;

    @Value("${normalRanges.diastolicPressure.min}")
    private int minDiastolicPressure;

    @Value("${normalRanges.diastolicPressure.max}")
    private int maxDiastolicPressure;

    @Value("${topic.name}")
    private String orderTopic;

    private String devicesUrl;

    private ObjectMapper objectMapper;

    private RedisTemplate<String, String> redisTemplate;

    private KafkaTemplate<String, String> kafkaTemplate;

    private RestTemplate restTemplate;

    private boolean redisDataSynced;

    public void processDevicePing(DevicePingDto devicePingDto) throws JsonProcessingException {
        if (!redisDataSynced) {
            //если в редисе нет данных, первый пинг сохраняется в редис и отправляется в кафку
            //+ ко всему, если данных в редисе нет, то надо их засинкать с активными данными из бд
            syncDataFromDatabase(devicePingDto);
            redisDataSynced = true;
        } else {
            //если данные есть
            String devicePing = objectMapper.writeValueAsString(devicePingDto);
            if (hasDeviation(devicePingDto)) {
                //проверяем отклонения
                //если есть, то скидываешь в тот топик, который сохраняет данные
                kafkaTemplate.send(orderTopic, devicePing);
            } else {
                //если отклонений нет, обновляешь редис
                redisTemplate.opsForList().rightPush(String.valueOf(devicePingDto.getDeviceId()), devicePing);
            }
        }
    }
    private void syncDataFromDatabase(DevicePingDto devicePingDto) throws JsonProcessingException {
        //отправл в кафку первый пинг
        var devicePing = objectMapper.writeValueAsString(devicePingDto);
        kafkaTemplate.send(orderTopic, devicePing);
        //сохраняем в редис первый пинг
        redisTemplate.opsForList().rightPush(String.valueOf(devicePingDto.getDeviceId()), devicePing);

        //синхронизуем с бд
        ResponseEntity<List<DevicePingDto>> dbDataResponse = restTemplate.exchange(
                devicesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        List<DevicePingDto> dbData = dbDataResponse.getBody();

        if (dbData != null && !dbData.isEmpty()) {
            //если есть данные в бд, обновляем редис
            for (DevicePingDto data : dbData) {
                String ping = objectMapper.writeValueAsString(data);
                redisTemplate.opsForList().rightPush(String.valueOf(data.getDeviceId()), ping);
            }
        }
    }
    private boolean hasDeviation(DevicePingDto devicePingDto) {
        if (devicePingDto.getWeight() < minWeight
                || devicePingDto.getWeight() > maxWeight) {
            return true;
        }

        if (devicePingDto.getHeight() < minHeight
                || devicePingDto.getHeight() > maxHeight) {
            return true;
        }

        if (devicePingDto.getPulse() < minPulse
                || devicePingDto.getPulse() > maxPulse) {
            return true;
        }

        if (devicePingDto.getTemperature() < minTemperature
                || devicePingDto.getTemperature() > maxTemperature) {
            return true;
        }

        if (devicePingDto.getHeartbeat() < minHeartbeat
                || devicePingDto.getHeartbeat() > maxHeartbeat) {
            return true;
        }

        if (devicePingDto.getRespiratoryRate() < minRespiratoryRate
                || devicePingDto.getRespiratoryRate() > maxRespiratoryRate) {
            return true;
        }

        if (devicePingDto.getSystolicPressure() < minSystolicPressure
                || devicePingDto.getSystolicPressure() > maxSystolicPressure) {
            return true;
        }

        if (devicePingDto.getDiastolicPressure() < minDiastolicPressure
                || devicePingDto.getDiastolicPressure() > maxDiastolicPressure) {
            return true;
        }

        return false;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Value("${ku-gateway.devices-url}")
    public void setDevicesUrl(String devicesUrl) {
        this.devicesUrl = devicesUrl;
    }
}
