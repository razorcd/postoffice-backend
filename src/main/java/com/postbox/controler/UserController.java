package com.postbox.controler;

import com.postbox.controler.dto.UserDto;
import com.postbox.controler.dto.param.UserParam;
import com.postbox.controler.mapper.UserMapper;
import com.postbox.document.User;
import com.postbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody  UserParam userParams) {
        User user = userService.create(userParams.getUsername().trim(), userParams.getPlainPassword().trim());
        return UserMapper.userToDto(user);
    }

}
