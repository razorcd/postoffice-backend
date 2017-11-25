package com.postbox.controler;

import com.postbox.controler.deserializer.CookieDeserielizer;
import com.postbox.controler.dto.IncomingRequestDto;
import com.postbox.controler.mapper.HttpRequestMapper;
import com.postbox.controler.mapper.IncomingRequestMapper;
import com.postbox.document.IncomingRequest;
import com.postbox.service.IncomingRequestService;
import com.postbox.controler.deserializer.HttpRequestDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BoxController {

    IncomingRequestService incomingRequestServiceImpl;

    HttpRequestDeserializer httpRequestDeserializer;
    CookieDeserielizer cookieDeserielizer;

    @Autowired
    public BoxController(IncomingRequestService incomingRequestServiceImpl,
                         HttpRequestDeserializer httpRequestDeserializer,
                         CookieDeserielizer cookieDeserielizer) {
        this.incomingRequestServiceImpl = incomingRequestServiceImpl;
        this.httpRequestDeserializer = httpRequestDeserializer;
        this.cookieDeserielizer = cookieDeserielizer;
    }

    /**
     * Endpoint to retrieve the entire request history
     *
      * @return list of all old requests
     */
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping(value = "/requests", method = RequestMethod.GET)
    public @ResponseBody List<IncomingRequestDto> getRequests() {
        List<IncomingRequest> incomingRequests = incomingRequestServiceImpl.getAll();
        List<IncomingRequestDto> incomingRequestDtos = incomingRequests.stream().
                map(incomingRequest -> IncomingRequestMapper.incomingRequestToDto(incomingRequest)).
                collect(Collectors.toList());

        incomingRequests.get(0).setId("0");
        incomingRequests.get(0).getHeaders().replace("Accept", "xxx");
        incomingRequests.get(0).getParams().
                replace("U91PZtp2BF3hh7OsO1n0eOFCrEm8gT8AgtoG8b540zIQ8RIk0HKLBcwDE7bq6CJi59o6X23wpD2HLn9zmDz7bSYK9OeaR6m8elxhLg261m3Q2kt0G26KmDUduTmO7KZ8l6FvGb2uWBe5akb", new String[]{"11","22"});
        incomingRequests.get(0).getParams().
                get("N05US82SqUPwEZ2eIUvLCcR85Ww12cG86lvtiYBDbpjTZ0cfC0IeZL2d20tk5sCpcXUs63T4ZQ8m33hSlms4sq2chcJguZ37nwk0duURT3hPdufRRZeVs9iOMa4dtX7fp6fyaXczigrHi4lrOvLb64eXhOu0aYbfx4PLwoqtCj9eCG3kVecoyDInIT436zJ0vk8t9POow57COW1a0CJMJQIr21203qr3R836KpIs3Bt7JOzHbT6IWauFj0WmJ98Z1bQoDxDHv7f4wEx5k1dJqp6SzE65lvGSTX2b1ZzR6JH9mF8QVdc4qZjz1Jg5D8D1vmHf6XuuFi7cvOZLDO4RPs4NLrqQU4HpGm456mxq28Y8ej9D017J9508NF1Ozve92XG7RYrGb1Ow7uSDiq7wgg9m7ISod")
                [0]="00000";
        return incomingRequestDtos;
    }

    /**
     * Endpoint to listen to any tipe of HTTP request.
     *
     * @param request the request object from the servlet
    * @param response the response object from the servlet
     * @return empty response with status 204
     */
    @RequestMapping(value = "/incoming/**")
    public ResponseEntity recordRequest(HttpServletRequest request, HttpServletResponse response) {

        //TODO: move this to a servlet parser
        IncomingRequest incomingRequest = new HttpRequestMapper(httpRequestDeserializer, cookieDeserielizer).httpServletRequestToIncomingRequestModel(request);
        incomingRequestServiceImpl.save(incomingRequest);


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
}
