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
