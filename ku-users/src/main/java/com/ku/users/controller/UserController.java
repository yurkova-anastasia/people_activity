package com.ku.users.controller;

import com.ku.common.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping()
    public UserDto find() {
        return new UserDto()
                .setName("nastya")
                .setSurname("yurkova");
    }
}
