package com.postbox.service;

import com.postbox.config.UserPrincipal;
import com.postbox.controler.dto.param.UserUpdateParam;
import com.postbox.document.User;

public interface UserService {

    /**
     * Create a User
     *
     * @param user the user object to create
     * @param plainPassword the password for the user
     * @return the created user with the persisted id and encoded password
     */
    public User create(User user, String plainPassword);

    /**
     * Get a User by it's username
     *
     * @param username to identify User
     * @return [User] the requested User
     * @throws [NotFoundException] if user is not found
     */
    User getUserByUsername(String username);

    /**
     * Update a User by it's username
     *
     * @param username to identity User
     * @param userUpdateParam user parameters to update
     * @return nothing
     */
    void updateUserByUsername(String username, UserUpdateParam userUpdateParam);
}
