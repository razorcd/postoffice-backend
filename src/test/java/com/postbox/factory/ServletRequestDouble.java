package com.postbox.factory;

import com.github.javafaker.Faker;
import com.postbox.utils.Helper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ServletRequestDouble {

    private Faker faker = new Faker();

    private String url;
    private String method;
    private String contentType;
    private Map<String, String[]> params;
    private Map<String, String> headers;
    private Cookie[] cookies;
    private String body;

    private MockHttpServletRequestBuilder mockHttpServletRequestBuilder;

    /**
     * A wrapper object that generates a MockHttpServletRequestBuilder with random data
     * but also allows access to all the generated properties for further data testing.
     */
    public ServletRequestDouble() {
        generatePropertiesWithRandomData();
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

    public MockHttpServletRequestBuilder getMockHttpServletRequestBuilder() {
        createMockHttpServletRequestBuilder();
        return mockHttpServletRequestBuilder;
    }

    private void generatePropertiesWithRandomData() {
        this.url = faker.lorem().characters(0,100);
        this.method = Helper.pickOne(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "HEAD", "OPTION"));
        this.contentType = Helper.pickOne(Arrays.asList(MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE));

        this.params = new HashMap<String, String[]>();
        this.params.put(
                faker.lorem().characters(1, 500, true),
                new String[]{faker.lorem().characters(1, 500, true)});
        this.params.put(
                faker.lorem().characters(1, 500, true),
                new String[]{
                        faker.lorem().characters(1, 500, true),
                        faker.lorem().characters(1, 500, true)});

        this.headers = new HashMap<String, String>();
        this.headers.put(faker.lorem().characters(1, 500, true),
                faker.lorem().characters(1, 500, true));

        Cookie[] tempCookies = { new Cookie(
                    faker.lorem().characters(1, 500, true),
                    faker.lorem().characters(0, 500, true)
                ),
                new Cookie(
                    faker.lorem().characters(1, 500, true),
                    faker.lorem().characters(0, 500, true)
                )};
        this.cookies = tempCookies;

        this.body = faker.lorem().characters(0,5000,true);
    }

    private void createMockHttpServletRequestBuilder() {
        this.mockHttpServletRequestBuilder = MockMvcRequestBuilders.request(this.getMethod(), URI.create(this.getUrl()));

        mockHttpServletRequestBuilder.contentType(this.getContentType());
        mockHttpServletRequestBuilder.accept(Helper.pickOne(Arrays.asList(MediaType.ALL_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE)));
        getHeaders().forEach((key, val) -> mockHttpServletRequestBuilder.header(key, val));
        getParams().forEach((key, val) -> mockHttpServletRequestBuilder.param(key, val));
        mockHttpServletRequestBuilder.cookie(getCookies());
        mockHttpServletRequestBuilder.content(this.getBody());
    }
}
