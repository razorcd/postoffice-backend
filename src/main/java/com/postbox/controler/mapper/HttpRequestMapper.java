package com.postbox.controler.mapper;

import com.postbox.controler.deserializer.CookieDeserielizer;
import com.postbox.controler.deserializer.HttpRequestDeserializer;
import com.postbox.document.Cookie;
import com.postbox.document.IncomingRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class HttpRequestMapper {

    HttpRequestDeserializer httpRequestDeserializer;
    CookieDeserielizer cookieDeserielizer;

    /**
     * Mapper to convert http servlet requests to dtos
     *
     * @param httpRequestDeserializer the http deserialiser used to parse the http request
     */
    public HttpRequestMapper(HttpRequestDeserializer httpRequestDeserializer,
                             CookieDeserielizer cookieDeserielizer) {
        this.httpRequestDeserializer = httpRequestDeserializer;
        this.cookieDeserielizer = cookieDeserielizer;
    }

    /**
     * Converts the http servlet request to a request dto
     *
     * @param request the http servlet request object
     * @return the request dto
     */
    public IncomingRequest httpServletRequestToIncomingRequestModel(HttpServletRequest request) {
        IncomingRequest incomingRequest = new IncomingRequest();

        incomingRequest.setUrl(httpRequestDeserializer.getUrl(request));
        incomingRequest.setMethod(httpRequestDeserializer.getMethod(request));
        incomingRequest.setParams(httpRequestDeserializer.getParams(request));
        incomingRequest.setHeaders(httpRequestDeserializer.getHeaders(request));
        incomingRequest.setBody(httpRequestDeserializer.getBody(request));

        incomingRequest.setCookies(servletCookieToCookieModel(request));

        return incomingRequest;
    }

    private List<Cookie> servletCookieToCookieModel(HttpServletRequest request) {
        AtomicReference<List<Cookie>> cookieList = new AtomicReference<>();

         Optional.ofNullable(httpRequestDeserializer.getCookies(request)).ifPresent( servletCookies -> {
             cookieList.set(Arrays.stream(servletCookies).map(servletCookie -> {
                        Cookie cookie = new Cookie();

                        cookie.setName(cookieDeserielizer.getName(servletCookie));
                        cookie.setName(cookieDeserielizer.getValue(servletCookie));
                        cookie.setVersion(cookieDeserielizer.getVersion(servletCookie));
                        cookie.setComment(cookieDeserielizer.getComment(servletCookie));
                        cookie.setDomain(cookieDeserielizer.getDomain(servletCookie));
                        cookie.setMaxAge(cookieDeserielizer.getMaxAge(servletCookie));
                        cookie.setPath(cookieDeserielizer.getPath(servletCookie));
                        cookie.setSecure(cookieDeserielizer.isSecure(servletCookie));
                        cookie.setHttpOnly(cookieDeserielizer.isHttpOnly(servletCookie));

                        return cookie;
                    }).collect(Collectors.toList()));
        });

         return cookieList.get();
    }
}
