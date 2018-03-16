package com.postbox.factory;

import com.github.javafaker.Faker;
import com.postbox.document.User;

public class UserDocumentFactory {

    private static Faker FAKER = new Faker();
//    private static Encoder encoder;  // TODO: inject password encoder

    public static User generateUser() {
        return createUserObject(FAKER.internet().password());
    }

    public static User generateUser(String password) {
        return createUserObject(password);
    }

    private static User createUserObject(String password) {

        User user = new User();

        user.setUsername(FAKER.lorem().characters(3,20, true));
        user.generatePathIdentifier();
        user.setEmail(FAKER.internet().emailAddress());
        user.setEncryptedPassword(encodePasswod(password));

        return user;
    }

    private static String encodePasswod(String password) {
//        return encoder.encode(password);
        return password;
    }

}
