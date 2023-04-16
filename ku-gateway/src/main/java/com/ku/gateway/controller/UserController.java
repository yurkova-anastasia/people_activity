package com.ku.gateway.controller;

import com.ku.gateway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @GetMapping(produces = "application/json")
    public String find() {
        return userService.find();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
