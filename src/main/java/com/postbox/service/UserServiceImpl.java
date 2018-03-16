package com.postbox.service;

import com.postbox.config.exceptions.EntityNotFoundException;
import com.postbox.controler.dto.param.UserUpdateParam;
import com.postbox.document.User;
import com.postbox.repository.UserNoSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    UserNoSqlRepository userNoSqlRepository;
    PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserNoSqlRepository userNoSqlRepository, PasswordEncoder encoder) {
        this.userNoSqlRepository = userNoSqlRepository;
        this.encoder = encoder;
    }

    @Override
    public User getUserByUsername(String username) {
        User user = userNoSqlRepository.findByUsername(username);
        ensureUserNotNull(user, "User with username "+username+" not found.");
        return user;
    }

    @Override
    public User create(User user, String plainPassword) {
        User newUser = new User(user.getUsername(), user.getEmail(), encoder.encode(plainPassword)); // to avoid muttation
        return userNoSqlRepository.save(newUser);
    }

    @Override
    public void updateUserByUsername(String username, UserUpdateParam userUpdateParam) {
        User user = userNoSqlRepository.findByUsername(username);
        ensureUserNotNull(user, "User with username "+username+" not found.");

        user.setEmail(userUpdateParam.getEmail());
        userNoSqlRepository.save(user);
    }

    @Override
    public User getUserByPathIdentifier(String pathIdentifier) {
        User user = userNoSqlRepository.findUserByPathIdentifier(pathIdentifier);
        ensureUserNotNull(user, "User with path identifier "+pathIdentifier+" not found.");

        return user;
    }

    private void ensureUserNotNull(User user, String errorMessage) {
        if (user == null) {throw new EntityNotFoundException(errorMessage);}
    }
}
