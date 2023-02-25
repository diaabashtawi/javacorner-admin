package com.bakheet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping(
            value = "/403"
    )
    public String votAuthorized(){
        return "403";
    }
}
