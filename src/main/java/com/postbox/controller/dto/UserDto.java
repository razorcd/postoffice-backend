package com.postbox.controller.dto;

import org.hibernate.validator.constraints.NotBlank;

public class UserDto {

    @NotBlank
    private String username;

    private String pathIdentifier;

    @NotBlank
    private String email;

    public UserDto() {
    }

    public UserDto(String username, String pathIdentifier, String email) {
        this.username = username;
        this.pathIdentifier = pathIdentifier;
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

    public String getPathIdentifier() {
        return pathIdentifier;
    }

    public void setPathIdentifier(String pathIdentifier) {
        this.pathIdentifier = pathIdentifier;
    }
}
