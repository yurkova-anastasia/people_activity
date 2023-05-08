package com.ku.users.mapper;

import com.ku.common.dto.UserDto;
import com.ku.common.dto.UserListDto;
import com.ku.common.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromDto(UserDto userDto);

    UserDto toDto(User user);

    List<User> fromDtoList(List<UserDto> userDto);

    List<UserDto> toDtoList(List<User> user);

    List<UserListDto> toUserListDto(List<User> user);
}
