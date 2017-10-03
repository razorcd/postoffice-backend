package com.postbox.factory;

import com.github.javafaker.Faker;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.net.URI;

public class HttpServletRequestFactory {


    private static Faker FAKER = new Faker();


    public static MockHttpServletRequestBuilder generate() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.request(HttpMethod.GET, URI.create(SERVICE_PATH+"/any_path"));
        request.content(FAKER.lorem().characters(0,5000,true));
        request.contentType(MediaType.ALL_VALUE);
        request.accept(MediaType.TEXT_PLAIN);
        request.cookie(
                new Cookie(
                    FAKER.lorem().characters(0, 500, true),
                    FAKER.lorem().characters(0, 500, true)
                ));
        request.header("X-TEST", "val-test");
        request.param("param1", "value1");

        return request;
    }

}
