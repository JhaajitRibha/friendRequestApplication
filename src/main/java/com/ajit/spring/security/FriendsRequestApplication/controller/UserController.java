package com.ajit.spring.security.FriendsRequestApplication.controller;

import com.ajit.spring.security.FriendsRequestApplication.model.Friend;
import com.ajit.spring.security.FriendsRequestApplication.repository.FriendRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/v1")
@RequiredArgsConstructor
public class UserController {

    private final FriendRepository friendsRepository;

    private final PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Friend friends) {
        try {
            String hashPwd = passwordEncoder.encode(friends.getPwd());
            friends.setPwd(hashPwd);
            Friend savedCustomer = friendsRepository.save(friends);

            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).
                        body("Given user details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("User registration failed");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An exception occurred: " + ex.getMessage());
        }

    }
}
