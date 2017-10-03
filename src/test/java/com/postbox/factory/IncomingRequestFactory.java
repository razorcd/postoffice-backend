package com.postbox.factory;

import com.github.javafaker.Faker;
import com.postbox.model.IncomingRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class IncomingRequestFactory {
    private static Faker FAKER = new Faker();

    public static IncomingRequest generate() {
        IncomingRequest incomingRequest = new IncomingRequest();

        incomingRequest.setUrl(FAKER.internet().url());
        incomingRequest.setMethod(FAKER.lorem().characters(2, 16, true));
        incomingRequest.setParams("{param1=1;param2=2}");
        incomingRequest.setHeaders("{header1=1;header2}");
        incomingRequest.setCookies("{cookie1=1;cookie2=2}");
        incomingRequest.setBody(FAKER.lorem().paragraph());

        return incomingRequest;
    }
}
