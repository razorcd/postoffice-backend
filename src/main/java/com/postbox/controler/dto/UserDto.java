package com.postbox.controler.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private String username;

    public UserDto() {
    }

    public UserDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
