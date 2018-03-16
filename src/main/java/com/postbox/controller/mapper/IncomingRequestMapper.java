package com.postbox.controller.mapper;

import com.postbox.controller.dto.CookieDto;
import com.postbox.controller.dto.IncomingRequestDto;
import com.postbox.document.Cookie;
import com.postbox.document.IncomingRequest;

import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class IncomingRequestMapper {

    public static IncomingRequestDto incomingRequestToDto(IncomingRequest incomingRequest) {
        IncomingRequestDto incomingRequestDto = new IncomingRequestDto();
        incomingRequestDto.setId(incomingRequest.getId());
        incomingRequestDto.setUrl(incomingRequest.getUrl());
        incomingRequestDto.setBody(incomingRequest.getBody());
        incomingRequestDto.setParams(paramsToMap(incomingRequest));
        incomingRequestDto.setHeaders(headersToMap(incomingRequest));
        incomingRequestDto.setMethod(incomingRequest.getMethod());
        incomingRequestDto.setCookies(incomingRequest.getCookies().stream().map(IncomingRequestMapper::cookieToDto).collect(Collectors.toList()));
        incomingRequestDto.setTimestamp(incomingRequest.getTimestamp().atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli());
        return incomingRequestDto;
    }

    public static CookieDto cookieToDto(Cookie cookie) {
        CookieDto cookieDto = new CookieDto();
        cookieDto.setName(cookie.getName());
        cookieDto.setValue(cookie.getValue());
        cookieDto.setVersion(cookie.getVersion());
        cookieDto.setDomain(cookie.getDomain());
        cookieDto.setMaxAge(cookie.getMaxAge());
        cookie.setPath(cookie.getPath());
        cookie.setHttpOnly(cookie.isHttpOnly());
        cookie.setSecure(cookie.isSecure());
        cookie.setComment(cookie.getComment());
        return cookieDto;
    }

    private static Map<String, String[]> paramsToMap(IncomingRequest incomingRequest) {
        Map<String, String[]> paramsMap = new HashMap<>();
        incomingRequest.getParams().forEach((key, val) ->
            paramsMap.put(key, val.clone())
        );
        return paramsMap;
    }

    private static Map<String,String> headersToMap(IncomingRequest incomingRequest) {
        Map<String, String> headersMap = new HashMap<>();
        incomingRequest.getHeaders().forEach((key, val) ->
            headersMap.put(key, val)
        );
        return headersMap;
    }

    private IncomingRequestMapper() {}
}
