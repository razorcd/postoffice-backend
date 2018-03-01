package com.postbox.repository;

import com.postbox.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserNoSqlRepository extends MongoRepository<User, Long> {
//    public List<User> findAll();
//
//    public User save(User user);
}
