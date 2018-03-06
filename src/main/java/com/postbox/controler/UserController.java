package com.postbox.controler;

import com.postbox.config.CustomAuthentication;
import com.postbox.controler.dto.UserDto;
import com.postbox.controler.dto.param.CreateUserParam;
import com.postbox.controler.dto.param.UserUpdateParam;
import com.postbox.controler.mapper.UserMapper;
import com.postbox.document.User;
import com.postbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;

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

    //TODO: Implement Security: authenticated and principal.username==username
    @GetMapping("/{username}")
    public UserDto getByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return UserMapper.userToDto(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody @Valid CreateUserParam createUserParams) {
        validatePasswordEqual(createUserParams.getPlainPassword(), createUserParams.getPlainPasswordConfirmation());

        User newUser = new User(createUserParams.getUsername(), createUserParams.getEmail(), null);
        User user = userService.create(newUser, createUserParams.getPlainPassword());

        customAuthentication.authenticateUserByUsername(user.getUsername());

        return UserMapper.userToDto(user);
    }

    @PatchMapping("/{username}")
    public void update(@PathVariable String username, @RequestBody UserUpdateParam userUpdateParam) {
        userService.updateUserByUsername(username, userUpdateParam);
    }


    private void validatePasswordEqual(String plainPassword, String plainPasswordConfirmation) {
        if (!plainPassword.equals(plainPasswordConfirmation)) {
            throw new ValidationException("Password confirmation must equal password.");
        };
    }
}
