package com.postbox.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private String username;
    private String encrypredPassword;

    public UserPrincipal() {
    }

    public UserPrincipal(String username, String encrypredPassword) {
        this.username = username;
        this.encrypredPassword = encrypredPassword;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncrypredPassword() {
        return encrypredPassword;
    }

    public void setEncrypredPassword(String encrypredPassword) {
        this.encrypredPassword = encrypredPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return encrypredPassword;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
