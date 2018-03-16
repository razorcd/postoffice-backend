package com.postbox.controller.mapper;

import com.postbox.controller.dto.UserDto;
import com.postbox.document.User;

public class UserMapper {

    public static UserDto userToDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setUsername(user.getUsername());
        userDto.setPathIdentifier(user.getPathIdentifier());
        userDto.setEmail(user.getEmail());

        return userDto;
    }

    private UserMapper() {}
}
