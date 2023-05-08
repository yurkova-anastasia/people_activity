package com.ku.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private RestTemplate restTemplate;

    public String findAll(Integer pageNumber, Integer pageSize){
        StringBuilder resourceUrl = new StringBuilder("http://localhost:8080/users/?");
        resourceUrl.append("&pageNumber=").append(pageNumber);
        resourceUrl.append("&pageSize=").append(pageSize);

        return restTemplate.getForObject(resourceUrl.toString(), String.class);
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
