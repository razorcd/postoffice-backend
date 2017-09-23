package com.postbox.postbox.controler;

import com.postbox.postbox.service.IncomingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BoxController {

    IncomingRequestService incomingRequestService;

    @Autowired
    public BoxController(IncomingRequestService incomingRequestService) {
        this.incomingRequestService = incomingRequestService;
    }

    @RequestMapping(value = "/**")
    public ResponseEntity<String> recordRequest(@RequestBody(required = false) String payload, HttpServletRequest request) {
        String fullRequest = payload; //TODO: add all request fields into an object
        incomingRequestService.save(fullRequest);
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}
