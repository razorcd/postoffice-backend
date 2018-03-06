package com.postbox.controler.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    public UserDto() {
    }

    public UserDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
