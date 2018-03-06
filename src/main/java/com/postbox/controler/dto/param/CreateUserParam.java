package com.postbox.controler.dto.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateUserParam {

    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "[a-zA-Z0-9_\\-.]+")
    private String username;

    @NotBlank
    @Pattern(regexp = "[^ @]+@[^ @]+\\.[^ @]+")
    private String email;

    @NotBlank
    @Size(min = 8, max = 256)
    @Pattern(regexp = "[^\\s\t\n\r\\h\\v]+")
    private String plainPassword;

    @NotBlank
    private String plainPasswordConfirmation;  //TODO: add custom validator to check password and passwordConfirmation equality


    public CreateUserParam() {
    }

    public CreateUserParam(String username, String email, String plainPassword, String plainPasswordConfirmation) {
        this.username = username;
        this.email = email;
        this.plainPassword = plainPassword;
        this.plainPasswordConfirmation = plainPasswordConfirmation;
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

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPlainPasswordConfirmation() {
        return plainPasswordConfirmation;
    }

    public void setPlainPasswordConfirmation(String plainPasswordConfirmation) {
        this.plainPasswordConfirmation = plainPasswordConfirmation;
    }
}
