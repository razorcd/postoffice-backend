package com.postbox.factory;

import com.github.javafaker.Faker;
import com.postbox.config.security.PasswordEncoderProvider;
import com.postbox.document.User;

public class UserDocumentFactory {

    private static Faker FAKER = new Faker();
    private static PasswordEncoderProvider encoder = new PasswordEncoderProvider();

    public static User generateUser() {
        return createUserObject();
    }

    public static User generateUser(String password) {
        User user = createUserObject();
        user.setEncryptedPassword(encodePasswod(password));
        return user;
    }

    public static User generateUser(String username, String password) {
        User user = createUserObject();
        user.setUsername(username);
        user.generatePathIdentifier();
        user.setEncryptedPassword(encodePasswod(password));
        return user;
    }

    private static User createUserObject() {
        User user = new User();
        user.setUsername(FAKER.lorem().characters(3,20, true));
        user.generatePathIdentifier();
        user.setEmail(FAKER.internet().emailAddress());
        user.setEncryptedPassword(encodePasswod(FAKER.internet().password(8,20)));
        return user;
    }

    private static String encodePasswod(String password) {
        return encoder.passwordEncoder().encode(password);
    }

}
