//package com.ku.gateway.util;
//
//import com.ku.common.dto.DevicePingDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//
//@Configuration
//@EnableScheduling
//public class DataSyncScheduler {
//    private RestTemplate restTemplate;
//
//    private RedisTemplate<Long, DevicePingDto> redisTemplate;
//
//    @Scheduled() // Запуск каждые 3 минуты
//    public void syncDataToRedis() {
//        // Получите актуальные данные из БД и обновите Redis
//        // Пример:
//        ResponseEntity<List<DevicePingDto>> dbDataResponse = restTemplate.exchange(
//                "http://localhost:8081/device-pings/" + devicePingDto.getDeviceId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                });
//
//        List<DevicePingDto> latestDataList = dbDataResponse.getBody();
//
//        for (DevicePingDto data : latestDataList) {
//            redisTemplate.opsForValue().set(data.getDeviceId(), data);
//        }
//    }
//
//
//    @Autowired
//    public void setRestTemplate(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    @Autowired
//    public void setRedisTemplate(RedisTemplate<Long, DevicePingDto> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//}