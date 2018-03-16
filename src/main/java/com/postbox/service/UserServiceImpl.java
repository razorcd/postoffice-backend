package com.postbox.service;

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
        return userNoSqlRepository.findByUsername(username);
    }

    @Override
    public User create(User user, String plainPassword) {
//        ensureUsernameUnique(user.getUsername());
//        ensureEmailUnique(user.getEmail());
        User newUser = new User(user.getUsername(), user.getEmail(), encoder.encode(plainPassword)); // to avoid muttation
        return userNoSqlRepository.save(newUser);
    }

    @Override
    public void updateUserByUsername(String username, UserUpdateParam userUpdateParam) {
//        ensureEmailUnique(userUpdateParam.getEmail());
        User user = userNoSqlRepository.findByUsername(username);
        user.setEmail(userUpdateParam.getEmail());
        userNoSqlRepository.save(user);
    }

    @Override
    public User getUserByPathIdentifier(String pathIdentifier) {
        return userNoSqlRepository.findUserByPathIdentifier(pathIdentifier);
    }

//    private void ensureUsernameUnique(String username) {
//        Assert.isNull(userNoSqlRepository.findByUsername(username), "Username already taken.");  // TODO: catch
//    }
//    private void ensureEmailUnique(String email) {
//        Assert.isNull(userNoSqlRepository.findByEmail(email), "Email already taken.");  // TODO: catch
//    }
}
