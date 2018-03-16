package com.postbox.document;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Document
public class IncomingRequest {

    @Id
    private String id;

    @NotBlank
    String userPathIdentifier;

    @NotBlank
    @Size(max=16664) // max 2083 characters
    private String url;

    @NotBlank
    @Size(max=16)
    private String method;

    @Size(max=65535)
    private Map<String, String[]> params;

    @Size(max=65535)
    private Map<String, String> headers;

    @NotNull
    @Size(max=32744) // cookie max size is 4093 bytes
    private List<Cookie> cookies = new ArrayList<>();

    @Size(max=16777216) //2MB
    private String body;

    @NotNull
    private LocalDateTime timestamp;

    public IncomingRequest() {
    }

    public IncomingRequest(String id, String userPathIdentifier, String url, String method, Map<String, String[]> params, Map<String, String> headers, List<Cookie> cookies, String body, LocalDateTime timestamp) {
        this.id = id;
        this.userPathIdentifier = userPathIdentifier;
        this.url = url;
        this.method = method;
        this.params = params;
        this.headers = headers;
        this.cookies = cookies;
        this.body = body;
        this.timestamp = timestamp;
    }

    /**
     * Clone constructor
     * @param ir
     */
    public IncomingRequest(IncomingRequest ir) {
        this(ir.id, ir.userPathIdentifier, ir.url, ir.method, ir.params, ir.headers, ir.cookies, ir.body, ir.timestamp);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserPathIdentifier() {
        return userPathIdentifier;
    }

    public void setUserPathIdentifier(String userPathIdentifier) {
        this.userPathIdentifier = userPathIdentifier;
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

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IncomingRequest that = (IncomingRequest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userPathIdentifier, that.userPathIdentifier) &&
                Objects.equals(url, that.url) &&
                Objects.equals(method, that.method) &&
                Objects.equals(params, that.params) &&
                Objects.equals(headers, that.headers) &&
                Objects.equals(cookies, that.cookies) &&
                Objects.equals(body, that.body) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userPathIdentifier, url, method, params, headers, cookies, body, timestamp);
    }

    @Override
    public String toString() {
        return "IncomingRequest{" +
                "id='" + id + '\'' +
                ", userPathIdentifier='" + userPathIdentifier + '\'' +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", params=" + params +
                ", headers=" + headers +
                ", cookies=" + cookies +
                ", body='" + body + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }


}
