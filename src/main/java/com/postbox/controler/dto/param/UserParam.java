package com.postbox.controler.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserParam {

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "[a-zA-Z0-9_\\-.]+")
    private String username;

    @NotBlank
    @Size(min = 8, max = 256)
    @Pattern(regexp = "[^\\s\t\n\r\\h\\v]+")
    private String plainPassword;


    public UserParam() {
    }

    public UserParam(String username, String plainPassword) {
        this.username = username;
        this.plainPassword = plainPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

}
