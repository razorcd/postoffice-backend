package com.postbox.service;

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

    public User create(String username, String plainPassword) {
        String encryptedPassword = encoder.encode(plainPassword);
        plainPassword = null;

        ensureUsernameUnique(username);
        User user = new User(username, encryptedPassword);
        return userNoSqlRepository.save(user);
    }

    private void ensureUsernameUnique(String username) {
        Assert.isNull(userNoSqlRepository.findByUsername(username), "Username already taken.");  // TODO: catch
    }
}
