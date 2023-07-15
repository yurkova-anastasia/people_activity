package com.ku.users.controller;

import com.ku.common.dto.AuthenticationResponseDto;
import com.ku.common.dto.UserRequestDto;
import com.ku.common.dto.UserResponseDto;
import com.ku.users.exception.ServiceException;
import com.ku.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserResponseDto> findAll(
        @Parameter(description = "Page number", example = "1")
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @Parameter(description = "Page size", example = "10")
        @RequestParam(value = "size", required = false, defaultValue = "20") Integer size
    ) {
        return userService.findAll(new UserRequestDto()
                .setPage(page)
                .setSize(size));
    }

    @GetMapping("/username")
    @Operation(summary = "Find user by username")
    public AuthenticationResponseDto findByUsername(
        @Parameter(description = "Username", example = "User123")
        @RequestParam(value = "username", required = false) String username
    ) throws ServiceException {
        return userService.findByUsername(username);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
