package com.postbox.service;

import com.postbox.document.User;

public interface UserService {

    /**
     * Create a User
     *
     * @param email of user
     * @param plainPassword plain text that will be encoded
     * @return the created user with the persisted id and encoded password
     */
    public User create(String email, String plainPassword);
}
