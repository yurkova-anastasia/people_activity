package com.ku.users.controller;

import com.ku.common.dto.UserDto;
import com.ku.common.dto.UserListDto;
import com.ku.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "The User API")
public class UserController {

    private UserService userService;

    @GetMapping()
    @Operation(summary = "Find all users")
    public List<UserListDto> findAll(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        return userService.findAll(pageNumber, pageSize);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
