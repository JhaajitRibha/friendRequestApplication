package com.ajit.spring.security.FriendsRequestApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/v1")
public class ContactController {

    @GetMapping("/contacts")
    public String getContact(){
        return "contacts";
    }
}
