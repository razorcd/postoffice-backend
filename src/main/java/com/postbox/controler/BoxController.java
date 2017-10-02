package com.postbox.controler;

import com.postbox.controler.deserializer.CookieDeserielizer;
import com.postbox.controler.mapper.HttpRequestMapper;
import com.postbox.model.IncomingRequest;
import com.postbox.service.IncomingRequestService;
import com.postbox.controler.deserializer.HttpRequestDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping(value = "/**")
    public ResponseEntity<String> recordRequest(HttpServletRequest request, HttpServletResponse response) {

        //TODO: move this to a servlet parser
        IncomingRequest incomingRequest = new HttpRequestMapper(httpRequestDeserializer, cookieDeserielizer).httpServletRequestToIncomingRequestModel(request);
        incomingRequestServiceImpl.save(incomingRequest);

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }
}
