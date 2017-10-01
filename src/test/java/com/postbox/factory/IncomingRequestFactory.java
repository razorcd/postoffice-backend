package com.postbox.factory;

import com.github.javafaker.Faker;
import com.postbox.model.IncomingRequest;

public class IncomingRequestFactory {

    public static IncomingRequest generate() {
        IncomingRequest incomingRequest = new IncomingRequest();

        incomingRequest.setBody(new Faker().lorem().paragraph());

        return incomingRequest;
    }
}
