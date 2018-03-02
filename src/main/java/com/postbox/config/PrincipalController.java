package com.postbox.config;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class PrincipalController {

    @GetMapping("/principal")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }
}
