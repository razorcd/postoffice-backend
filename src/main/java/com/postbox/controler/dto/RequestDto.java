package com.postbox.controler.dto;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class RequestDto {

    private String url;
    private String method;
    private Map<String, String[]> params;
    private Map<String, String> headers;
    private Cookie[] cookies;
    private String body;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestDto that = (RequestDto) o;
        return Objects.equals(url, that.url) &&
                Objects.equals(method, that.method) &&
                Objects.equals(params, that.params) &&
                Objects.equals(headers, that.headers) &&
                Arrays.equals(cookies, that.cookies) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(url, method, params, headers, body);
        result = 31 * result + Arrays.hashCode(cookies);
        return result;
    }

    @Override
    public String toString() {
        return "RequestDto{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", params=" + params +
                ", headers=" + headers +
                ", cookies=" + Arrays.toString(cookies) +
                ", body='" + body + '\'' +
                '}';
    }
}
