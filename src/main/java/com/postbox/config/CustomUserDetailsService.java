package com.postbox.config;

import com.postbox.document.User;
import com.postbox.repository.UserNoSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserNoSqlRepository userNoSqlRepository;

    /**
     * Creates the UserDetails by user's username. Method is used by Spring Security to authenticate users.
     * @param username to indentify user
     * @return (UserDetails) the UserDetails principal. Can be casted to (UserPrincipal)
     * @throws UsernameNotFoundException if username is not found in db
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userNoSqlRepository.findByUsername(username);
        return new UserPrincipal(username, user.getEncryptedPassword());
    }
}
