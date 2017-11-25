package com.postbox.controler.mapper;

import com.postbox.controler.dto.CookieDto;
import com.postbox.controler.dto.IncomingRequestDto;
import com.postbox.document.Cookie;
import com.postbox.document.IncomingRequest;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

public class IncomingRequestMapper {

    public static IncomingRequestDto incomingRequestToDto(IncomingRequest incomingRequest) {
        IncomingRequestDto incomingRequestDto = new IncomingRequestDto();
        incomingRequestDto.setId(incomingRequest.getId());
        incomingRequestDto.setUrl(incomingRequest.getUrl());
        incomingRequestDto.setBody(incomingRequest.getBody());
        incomingRequestDto.setParams(incomingRequest.getParams());
        incomingRequestDto.setHeaders(incomingRequest.getHeaders());
        incomingRequestDto.setMethod(incomingRequest.getMethod());
        incomingRequestDto.setCookies(incomingRequest.getCookies().stream().map(cookie -> IncomingRequestMapper.cookieToDto(cookie)).collect(Collectors.toList()));
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
}
