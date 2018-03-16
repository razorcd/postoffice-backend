package com.postbox.factory;

import com.github.javafaker.Faker;
import com.postbox.document.Cookie;
import com.postbox.document.IncomingRequest;
import com.postbox.utils.Helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IncomingRequestDocumentFactory {
    private static Faker FAKER = new Faker();

    public static IncomingRequest generateIncomingRequest() {
        IncomingRequest incomingRequest = new IncomingRequest();

        incomingRequest.setUrl(FAKER.internet().url());
        incomingRequest.setMethod(FAKER.lorem().characters(2, 16, true));

        Map<String, String[]> params = new HashMap<>();
        params.put("param1", new String[]{"p1"} );
        params.put("param2", new String[]{"p1", "p2", "p3"} );
        incomingRequest.setParams(params);

        Map<String, String> headers = new HashMap<>();
        headers.put("header1", "hv1");
        headers.put("header2", "hv2");
        incomingRequest.setHeaders(headers);

        incomingRequest.setCookies(generateCookieList());
        incomingRequest.setBody(FAKER.lorem().paragraph());
        incomingRequest.setTimestamp(Helper.randomInstant(Instant.EPOCH, Instant.now()).atZone(ZoneId.systemDefault()).toLocalDateTime());
        incomingRequest.setUserPathIdentifier(FAKER.lorem().characters(12));
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
