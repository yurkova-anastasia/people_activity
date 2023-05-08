package com.ku.users.service;

import com.ku.common.dto.UserDto;
import com.ku.common.dto.UserListDto;
import com.ku.common.entity.User;
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


    public List<UserListDto> findAll(Integer pageNumber, Integer pageSize) {
        System.out.println(pageNumber + " " + pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<User> users = userRepository.findAll(pageRequest);
        return userMapper.toUserListDto(users.stream().toList());
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserListMapper(UserMapper userListMapper) {
        this.userMapper = userListMapper;
    }
}
