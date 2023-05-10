package com.ku.gateway.service;

import com.ku.common.dto.UserRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private RestTemplate restTemplate;

    private String usersUrl;

    public String findAll(UserRequestDto userRequestDto){
        var resourceUrl = new StringBuilder(usersUrl)
                .append("?&page=").append(userRequestDto.getPage())
                .append("&size=").append(userRequestDto.getSize());
        return restTemplate.getForObject(resourceUrl.toString(), String.class);
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${ku-gateway.users-url}")
    public void setUsersUrl(String usersUrl) {
        this.usersUrl = usersUrl;
    }
}
