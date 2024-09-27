package com.ajit.spring.security.FriendsRequestApplication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1")
public class TestingController {
    @GetMapping("/testing")
    public String sentResponse(){
        return "testing the tester ..";

    }
}
