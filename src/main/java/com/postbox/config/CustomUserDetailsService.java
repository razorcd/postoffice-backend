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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userNoSqlRepository.findByUsername(username);
        return new UserPrincipal(username, user.getEncryptedPassword());
    }
}
