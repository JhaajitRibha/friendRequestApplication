package com.ajit.spring.security.FriendsRequestApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/v1")
public class CardsController {
    @GetMapping("/cards")
    public String cards(){
        return "cards";
    }
}
