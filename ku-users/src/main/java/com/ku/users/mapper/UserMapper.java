package com.ku.users.mapper;

import com.ku.common.dto.UserResponseDto;
import com.ku.users.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserResponseDto> toUserResponseDto(List<User> user);
}
