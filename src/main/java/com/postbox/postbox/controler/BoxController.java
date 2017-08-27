package com.postbox.postbox.controler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BoxController {

    @RequestMapping(value = "/**")
    public ResponseEntity<String> recordRequest(@RequestBody(required = false) String payload, HttpServletRequest request) {
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}
