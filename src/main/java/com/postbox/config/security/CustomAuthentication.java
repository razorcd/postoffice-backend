package com.postbox.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthentication {

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public CustomAuthentication(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Authenticates user in Spring SecurityContext. Create security session. Does not create remember me session.
     * Litteraly logs use in.
     * @param username to indetify the user.
     * @returns nothing
     * @throws UsernameNotFoundException if username is not found in db
     */
    public void authenticateUserByUsername(String username) throws UsernameNotFoundException {
        UserPrincipal userPrincipal = (UserPrincipal) this.customUserDetailsService.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
