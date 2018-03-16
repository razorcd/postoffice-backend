package com.postbox.repository;

import com.postbox.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserNoSqlRepository extends MongoRepository<User, Long> {
//    List<User> findAll();
//
//    User save(User user);

    User findByUsername(String username);
    User findByEmail(String email);
    User findUserByPathIdentifier(String pathIdentifier);
}
