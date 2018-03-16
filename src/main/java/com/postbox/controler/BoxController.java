package com.postbox.controler;

import com.postbox.controler.deserializer.CookieDeserielizer;
import com.postbox.controler.deserializer.HttpRequestDeserializer;
import com.postbox.controler.dto.IncomingRequestDto;
import com.postbox.controler.mapper.HttpRequestMapper;
import com.postbox.controler.mapper.IncomingRequestMapper;
import com.postbox.document.IncomingRequest;
import com.postbox.document.User;
import com.postbox.service.IncomingRequestService;
import com.postbox.service.UserService;
import com.postbox.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BoxController {

    IncomingRequestService incomingRequestServiceImpl;
    UserService userService;

    HttpRequestDeserializer httpRequestDeserializer;
    CookieDeserielizer cookieDeserielizer;

    @Autowired
    public BoxController(IncomingRequestService incomingRequestServiceImpl,
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
    @CrossOrigin(origins = "*", maxAge = 3600)
    // TODO: spring security principal.getUsername == username
    @RequestMapping(value = "/user/{username}/requests", method = RequestMethod.GET)
    public @ResponseBody List<IncomingRequestDto> getRequests(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
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


//        JavaObjectSerializer.write(request, response);

//        response.reset();
//        response.setStatus(HttpStatus.NO_CONTENT.value());

//        for (Cookie cookie : request.getCookies()) {
//            cookie.setValue("");
//            cookie.setMaxAge(0);
//            cookie.setPath("/");
//
//            response.addCookie(cookie);
//        }
//
//
//        for (String headerName : response.getHeaderNames()) {
//            request.getHeader(headerName);
//        }



//        response.addIntHeader("Refresh", 3);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping("/private")
    @ResponseBody
    public String getPrivate() {
        return "private";
    }

}
