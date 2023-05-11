package com.ku.users.service;

import com.ku.common.dto.UserRequestDto;
import com.ku.common.dto.UserResponseDto;
import com.ku.users.entity.User;
import com.ku.users.mapper.UserMapper;
import com.ku.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    private UserMapper userMapper;


    public List<UserResponseDto> findAll(UserRequestDto userRequestDto) {
        var pageRequest = PageRequest.of(userRequestDto.getPage(), userRequestDto.getSize());
        var users = userRepository.findAll(pageRequest);
        return userMapper.toUserResponseDto(users.getContent());
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
