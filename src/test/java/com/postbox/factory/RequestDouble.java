package com.postbox.factory;

import com.github.javafaker.Faker;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestDouble {

    private String SERVICE_PATH = "/incoming";
    private Faker faker = new Faker();

    private String url;
    private String method;
    private String contentType;
    private Map<String, String[]> params;
    private Map<String, String> headers;
    private Cookie[] cookies;
    private String body;

    public RequestDouble() {
        this.url = SERVICE_PATH+faker.lorem().characters(0,100);
        this.method = pickOne(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTION"));
        this.contentType = pickOne(Arrays.asList(MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE));

        this.params = new HashMap<String, String[]>();

        String[] paramValue = {"value1"};
        this.params.put("param1", paramValue);
        this.headers = new HashMap<String, String>();
        this.headers.put("header1", "value1");

        Cookie[] tempCookies = { new Cookie(
                faker.lorem().characters(0, 500, true),
                faker.lorem().characters(0, 500, true)
        ))}
        this.cookies = tempCookies;

        this.body = faker.lorem().characters(0,5000,true);
    }

    private String pickOne(List<String> list) {
        return list.get((int)(Math.random() * list.size()));
    }

    public MockHttpServletRequestBuilder generate() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.request(this.getMethod(), URI.create(this.getUrl()));
        request.content(this.getBody());
        request.contentType(this.getContentType());
        request.accept(MediaType.TEXT_PLAIN);
        request.cookie(getCookies());
        request.header(getHeaders().get(0), getHeaders().get(1));
        request.param(getParams().get(0), getParams().get(0));
        return request;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String[]> getParams() {
        return params;
    }

    public void setParams(Map<String, String[]> params) {
        this.params = params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
