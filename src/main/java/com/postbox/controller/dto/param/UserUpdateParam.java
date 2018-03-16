package com.postbox.controller.dto.param;

public class UserUpdateParam {

    private String email;

    public UserUpdateParam() {
    }

    public UserUpdateParam(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
