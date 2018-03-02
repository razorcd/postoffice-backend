package com.postbox.factory;

import com.github.javafaker.Faker;
import com.postbox.document.User;

public class UserDocumentFactory {

    private static Faker FAKER = new Faker();
//    private static Encoder encoder;

    public static User generateUser() {
        String encryptedPassword = encodePasswod(FAKER.internet().password());
        return createUserObject(encryptedPassword);
    }

    public static User generateUser(String password) {
        String encryptedPassword = encodePasswod(password);
        return createUserObject(password);
    }

    private static User createUserObject(String encryptedPassword) {

        User user = new User();

        user.setUsername(FAKER.name().username());
        user.setEncryptedPassword(encryptedPassword);

        return user;
    }

    private static String encodePasswod(String password) {
//        return encoder.encode(password);
        return password;
    }

}
