package com.ajit.spring.security.FriendsRequestApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/rest/v1")
public class WelcomeController {
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}
