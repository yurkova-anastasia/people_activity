package com.ku.gateway.controller;

import com.ku.common.dto.UserRequestDto;
import com.ku.gateway.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User controller", description = "The User API")
public class UserController {

    private UserService userService;

    @PreAuthorize("hasAuthority('USER::READ')")
    @GetMapping(produces = "application/json")
    @Operation(summary = "Find all users")
    public String findAll(
        @Parameter(description = "Page number", example = "1")
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @Parameter(description = "Page size", example = "10")
        @RequestParam(value = "size", required = false, defaultValue = "20") Integer size
    ) {
        return userService.findAll(new UserRequestDto()
                .setPage(page)
                .setSize(size));
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
