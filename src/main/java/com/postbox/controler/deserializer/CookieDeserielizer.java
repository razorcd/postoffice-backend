package com.postbox.controler.deserializer;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;

@Component
public class CookieDeserielizer {

    public String getName(Cookie cookie) {
        return cookie.getName();
    }

    public String getValue(Cookie cookie) {
        return cookie.getValue();
    }

    public int getVersion(Cookie cookie) {
        return cookie.getVersion();
    }

    public String getComment(Cookie cookie) {
        return cookie.getComment();
    }

    public String getDomain(Cookie cookie) {
        return cookie.getDomain();
    }

    public int getMaxAge(Cookie cookie) {
        return cookie.getMaxAge();
    }

    public String getPath(Cookie cookie) {
        return cookie.getPath();
    }

    public boolean isSecure(Cookie cookie) {
        return cookie.getSecure();
    }

    public boolean isHttpOnly(Cookie cookie) {
        return cookie.isHttpOnly();
    }
}
