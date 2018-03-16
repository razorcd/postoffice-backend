package com.postbox.controller;

import com.postbox.config.security.CustomAuthentication;
import com.postbox.config.exceptions.ValidationException;
import com.postbox.config.exceptions.ValidationFieldException;
import com.postbox.controller.dto.UserDto;
import com.postbox.controller.dto.param.CreateUserParam;
import com.postbox.controller.dto.param.UserUpdateParam;
import com.postbox.controller.mapper.UserMapper;
import com.postbox.document.User;
import com.postbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private CustomAuthentication customAuthentication;

    @Autowired
    public UserController(UserService userService, CustomAuthentication customAuthentication) {
        this.userService = userService;
        this.customAuthentication = customAuthentication;
    }

    @GetMapping("/{username}")
    @PreAuthorize("#username == principal.username")
    public UserDto getByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return UserMapper.userToDto(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid CreateUserParam createUserParams) {
        validatePasswordEqual(CreateUserParam.class, createUserParams.getPlainPassword(), createUserParams.getPlainPasswordConfirmation());

        User newUser = new User(createUserParams.getUsername(), createUserParams.getEmail(), null);
        User user = userService.create(newUser, createUserParams.getPlainPassword());

        customAuthentication.authenticateUserByUsername(user.getUsername());

        return UserMapper.userToDto(user);
    }

    @PatchMapping("/{username}")
    @PreAuthorize("#username == principal.username")
    public void update(@PathVariable String username, @RequestBody UserUpdateParam userUpdateParam) {
        userService.updateUserByUsername(username, userUpdateParam);
    }

    private void validatePasswordEqual(Class object, String plainPassword, String plainPasswordConfirmation) {
        if (!plainPassword.equals(plainPasswordConfirmation)) {
            throw new ValidationException("Invalid parameters.", new ValidationFieldException(object, "plainPasswordConfirmation", plainPasswordConfirmation, "must equal password"));
        };
    }
}
