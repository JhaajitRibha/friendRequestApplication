package com.ajit.spring.security.FriendsRequestApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AccessDeniedController {

    @GetMapping("/denied")
    public String accessDeniedPage(Model model) {

        model.addAttribute("error", "ACCESS_DENIED");
        model.addAttribute("message", "You do not have permission to access this resource.");
        model.addAttribute("status", 403); // Forbidden status
        model.addAttribute("timestamp", new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss").format(new Date()));

        return "accessDenied";
    }
}
