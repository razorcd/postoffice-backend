package com.postbox.controller;

import com.postbox.config.exceptions.EntityNotFoundException;
import com.postbox.controller.deserializer.CookieDeserielizer;
import com.postbox.controller.deserializer.HttpRequestDeserializer;
import com.postbox.controller.dto.IncomingRequestDto;
import com.postbox.controller.mapper.HttpRequestMapper;
import com.postbox.controller.mapper.IncomingRequestMapper;
import com.postbox.document.IncomingRequest;
import com.postbox.document.User;
import com.postbox.service.IncomingRequestService;
import com.postbox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IncomingRequestController {

    IncomingRequestService incomingRequestServiceImpl;
    UserService userService;

    HttpRequestDeserializer httpRequestDeserializer;
    CookieDeserielizer cookieDeserielizer;

    @Autowired
    public IncomingRequestController(IncomingRequestService incomingRequestServiceImpl,
                                     UserService userService,
                                     HttpRequestDeserializer httpRequestDeserializer,
                                     CookieDeserielizer cookieDeserielizer) {
        this.incomingRequestServiceImpl = incomingRequestServiceImpl;
        this.userService = userService;
        this.httpRequestDeserializer = httpRequestDeserializer;
        this.cookieDeserielizer = cookieDeserielizer;
    }

    /**
     * Endpoint to retrieve the request history
     *
     * @return list of all old requests
     */
    @RequestMapping(value = "/users/{username}/incomingrequests", method = RequestMethod.GET)
    @PreAuthorize("#username == principal.username")
    public @ResponseBody List<IncomingRequestDto> getIncomingRequests(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        if (user == null) {throw new EntityNotFoundException("User with username "+username+" not found.");}

        List<IncomingRequest> incomingRequests = incomingRequestServiceImpl.getByUserPathIdentifier(user.getPathIdentifier());
        List<IncomingRequestDto> incomingRequestDtos = incomingRequests.stream().
                map(IncomingRequestMapper::incomingRequestToDto).
                collect(Collectors.toList());
        Collections.reverse(incomingRequestDtos);

        return incomingRequestDtos;
    }

    /**
     * Endpoint to send any type of HTTP request.
     *
     * @param request the request object from the servlet
     * @param response the response object from the servlet
     * @return empty response with status 204
     */
    //TODO: move this to a servlet filter?
    @RequestMapping(value = "/incoming/{pathIdentifier}/**")
    public ResponseEntity recordRequest(HttpServletRequest request, HttpServletResponse response, @PathVariable String pathIdentifier) {
        Assert.hasLength(pathIdentifier, "PathIdentifier parameter is required.");
        User user = userService.getUserByPathIdentifier(pathIdentifier);

        if (user != null) {
            IncomingRequest incomingRequest = new HttpRequestMapper(httpRequestDeserializer, cookieDeserielizer).httpServletRequestToIncomingRequestModel(request);
            incomingRequestServiceImpl.save(incomingRequest, pathIdentifier);
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
