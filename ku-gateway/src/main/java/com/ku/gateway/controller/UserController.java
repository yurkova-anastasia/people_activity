package com.ku.gateway.controller;

import com.ku.common.dto.UserRequestDto;
import com.ku.gateway.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "The User API")
public class UserController {

    private UserService userService;

    @GetMapping(produces = "application/json")
    @Operation(summary = "Find all users")
    public String findAll(
        @Parameter(description = "Page number", example = "1")
        @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
        @Parameter(description = "Page size", example = "10")
        @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize
    ) {
        return userService.findAll(new UserRequestDto()
                .setPageNumber(pageNumber)
                .setPageSize(pageSize));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
