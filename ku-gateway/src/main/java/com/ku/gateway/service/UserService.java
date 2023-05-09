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
                .append("?&pageNumber=").append(userRequestDto.getPageNumber())
                .append("&pageSize=").append(userRequestDto.getPageSize());
        return restTemplate.getForObject(resourceUrl.toString(), String.class);
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${gateway.users-url}")
    public void setUsersUrl(String usersUrl) {
        this.usersUrl = usersUrl;
    }
}
