package com.postbox.config.security;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class PrincipalController {

    /**
     * Get current logged in User Principal from Spring Context
     *
     * @param principal injected by Spring
     * @return current Principal
     */
    @GetMapping("/principal")
    public Principal getPrincipal(Principal principal) {
        return principal;
    }
}
