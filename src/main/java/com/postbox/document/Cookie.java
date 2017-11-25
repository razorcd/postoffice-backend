package com.postbox.document;

import javax.validation.constraints.NotNull;

public class Cookie {

    @NotNull
    private String name;

    @NotNull
    private String value;

    private int version;

    //
    // Attributes encoded in the header's cookie fields.
    //

    @NotNull
    private String domain; // ;Domain=VALUE ... domain that sees cookie

    @NotNull
    private int maxAge = -1; // ;Max-Age=VALUE ... cookies auto-expire

    @NotNull
    private String path; // ;Path=VALUE ... URLs that see the cookie

    @NotNull
    private boolean httpOnly; // Not in cookie specs, but supported by browsers

    private boolean secure; // ;Secure ... e.g. use SSL

    private String comment; // ;Comment=VALUE ... describes cookie's use


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
