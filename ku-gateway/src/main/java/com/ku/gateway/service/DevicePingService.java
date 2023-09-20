package com.ku.gateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ku.common.dto.DevicePingDto;
import com.ku.gateway.service.producer.DevicePingProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DevicePingService {
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

    private ObjectMapper objectMapper;

    private RedisTemplate<String, String> redisTemplate;

    private DevicePingProducer producer;

    public void save(DevicePingDto devicePingDto) throws JsonProcessingException {
        if (hasDeviation(devicePingDto)) {
            //проверяем отклонения
            //если есть, то скидываешь в тот топик, который сохраняет данные
            producer.sendMessage(devicePingDto);
        } else {
            //если отклонений нет, обновляешь редис
            String devicePing = objectMapper.writeValueAsString(devicePingDto);
            redisTemplate.opsForList().rightPush(String.valueOf(devicePingDto.getDeviceId()), devicePing);
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
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setProducer(DevicePingProducer producer) {
        this.producer = producer;
    }
}
