package com.ku.users.service;

import com.ku.common.dto.AuthenticationUserDto;
import com.ku.common.dto.UserRequestDto;
import com.ku.common.dto.UserResponseDto;
import com.ku.users.entity.Authority;
import com.ku.users.entity.User;
import com.ku.users.exception.ServiceException;
import com.ku.users.mapper.UserMapper;
import com.ku.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;


    public List<UserResponseDto> findAll(UserRequestDto userRequestDto) {
        var pageRequest = PageRequest.of(userRequestDto.getPage(), userRequestDto.getSize());
        var users = userRepository.findAll(pageRequest);
        return userMapper.toUserResponseDto(users.getContent());
    }

    public AuthenticationUserDto findByUsername(String username) throws ServiceException {
        var user = userRepository.findByUsername(username);
        var userDto = fillAuthenticationUserDto(user);
        if (userDto == null) {
            throw new ServiceException(String.format("User with username = %s not found", username), HttpStatus.NOT_FOUND);
        } else {
            return userDto;
        }
    }

    private static AuthenticationUserDto fillAuthenticationUserDto(User user) {
        return new AuthenticationUserDto()
                          .setId(user.getId())
                          .setUsername(user.getUsername())
                          .setPassword(user.getPassword())
                          .setAuthorities(user.getRoles().stream()
                                              .flatMap(role -> role.getAuthorities().stream())
                                              .map(Authority::getAuthorityName)
                                              .collect(Collectors.toSet()));
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
