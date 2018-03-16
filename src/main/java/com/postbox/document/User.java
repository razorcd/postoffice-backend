package com.postbox.document;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Document
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    private String pathIdentifier;

    @Indexed(unique = true)
    @NotBlank
    @Size(min = 3, max = 64)
    @Pattern(regexp = "[a-zA-Z0-9_\\-]+")
    private String username;

    @Indexed(unique = true)
    @NotBlank
    @Pattern(regexp = "[^ @]+@[^ @]+\\.[^ @]+")
    private String email;

    @NotBlank
    private String encryptedPassword;

    public User() {
    }

    /**
     * Initilize the User with specified arguments and generate pathIdentifier
     * @param username
     * @param email
     * @param encryptedPassword
     */
    public User(String username, String email, String encryptedPassword) {
        this.username = username;
        this.generatePathIdentifier();
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPathIdentifier() {
        return pathIdentifier;
    }

    public void setPathIdentifier(String pathIdentifier) {
        this.pathIdentifier = pathIdentifier;
    }

    public void generatePathIdentifier() {
        char lastChar = this.username.toCharArray()[this.username.length()-1];
        setPathIdentifier(this.generatePseudoRandomValue(String.valueOf(lastChar)));
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

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(pathIdentifier, user.pathIdentifier) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(encryptedPassword, user.encryptedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pathIdentifier, username, email, encryptedPassword);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", pathIdentifier='" + pathIdentifier + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                '}';
    }

    private String generatePseudoRandomValue(String salt) {
        Assert.hasLength(salt, "Salt value must not be empty.");
        return Integer.toHexString(salt.hashCode()) + Long.toHexString(new Date().getTime());
    }
}
