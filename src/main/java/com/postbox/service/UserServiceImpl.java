package com.postbox.service;

import com.postbox.document.User;
import com.postbox.repository.UserNoSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserNoSqlRepository userNoSqlRepository;
//    Encoder encoder;

    @Autowired
    public UserServiceImpl(UserNoSqlRepository userNoSqlRepository) {
        this.userNoSqlRepository = userNoSqlRepository;
    }

    public User create(String email, String plainPassword) {
        String encryptedPassword = plainPassword; //encoder.encode(plainPassword);
        plainPassword = null;

        User user = new User(email, encryptedPassword);

        return userNoSqlRepository.save(user);
    }
}
