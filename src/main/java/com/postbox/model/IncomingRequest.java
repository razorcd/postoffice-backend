package com.postbox.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "incoming_requests")
public class IncomingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    @NotNull
    @Size(max=16664) // max 2083 characters
    private String url;

    @NotNull
    @Column(length = 16)
    @Size(max=16)
    private String method;

    @Lob
    @Size(max=65535)
    private String params;

    @Lob
    @Size(max=65535)
    private String headers;

    @Lob
    @Size(max=32744) // cookie max size is 4093 bytes
    private String cookies;

    @Lob
    @Size(max=16777216) //2MB
    private String body;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
