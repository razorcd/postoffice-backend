package com.postbox.factory;

import com.github.javafaker.Faker;
import com.postbox.document.Cookie;
import com.postbox.document.IncomingRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IncomingRequestDocumentFactory {
    private static Faker FAKER = new Faker();

    public static IncomingRequest generateIncomingRequest() {
        IncomingRequest incomingRequest = new IncomingRequest();

        incomingRequest.setUrl(FAKER.internet().url());
        incomingRequest.setMethod(FAKER.lorem().characters(2, 16, true));
        incomingRequest.setParams("{param1=1;param2=2}");
        incomingRequest.setHeaders("{header1=1;header2}");
        incomingRequest.setCookies(generateCookieList());
        incomingRequest.setBody(FAKER.lorem().paragraph());

        return incomingRequest;
    }

    public static List<Cookie> generateCookieList() {
        return Arrays.stream(new Cookie[FAKER.random().nextInt(10)]).map(v -> generateCookie()).collect(Collectors.toList());
    }

    public static Cookie generateCookie() {
        Cookie cookie = new Cookie();
        cookie.setName(FAKER.lorem().characters(2,100,true));
        cookie.setDomain(FAKER.internet().domainName());
        cookie.setComment(FAKER.lorem().characters(0,500,true));
        cookie.setHttpOnly(FAKER.bool().bool());
        cookie.setMaxAge(FAKER.number().numberBetween(0,32000));
        cookie.setPath(FAKER.lorem().characters(0,100));
        cookie.setSecure(FAKER.bool().bool());
        cookie.setVersion(FAKER.number().numberBetween(0,32000));
        cookie.setValue(FAKER.lorem().characters(0,500, true));
        return cookie;
    }
}
