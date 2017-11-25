package com.postbox.document;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

public class IncomingRequest {

    @Id
    private String id;

    @NotNull
    @Size(max=16664) // max 2083 characters
    private String url;

    @NotNull
    @Size(max=16)
    private String method;

    @Size(max=65535)
    private Map<String, String[]> params;

    @Size(max=65535)
    private Map<String, String> headers;

    @Size(max=32744) // cookie max size is 4093 bytes
    private List<Cookie> cookies;

    @Size(max=16777216) //2MB
    private String body;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Cookie> getCookies() {
        return cookies;
    }

    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
