package com.postbox.postbox.controler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BoxController {

    @RequestMapping(value = "/**")
    public ResponseEntity<String> recordRequest(Model model) {
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}
