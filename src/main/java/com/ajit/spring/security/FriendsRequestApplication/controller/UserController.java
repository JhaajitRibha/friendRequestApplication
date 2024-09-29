package com.ajit.spring.security.FriendsRequestApplication.controller;

import com.ajit.spring.security.FriendsRequestApplication.constants.ApplicationConstants;
import com.ajit.spring.security.FriendsRequestApplication.model.Friend;
import com.ajit.spring.security.FriendsRequestApplication.model.LoginRequestDTO;
import com.ajit.spring.security.FriendsRequestApplication.model.LoginResponseDTO;
import com.ajit.spring.security.FriendsRequestApplication.repository.FriendRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/v1")
@RequiredArgsConstructor
public class UserController {

    private final FriendRepository friendsRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final Environment environment;

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

    @RequestMapping("/user")
    public Friend getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Friend> optionalCustomer =friendsRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }

    @PostMapping("/apiLogin")
    public ResponseEntity<LoginResponseDTO> apiLogin(@RequestBody LoginRequestDTO loginRequestDto) {

        String jwt=null;
        Authentication authentication
                = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDto.username(), loginRequestDto.password());

        Authentication authResponse = authenticationManager.authenticate(authentication);
        if (null != authResponse && authResponse.isAuthenticated()) {
            if (null != environment) {
                String secret = environment.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                jwt = Jwts.builder().issuer("Friend Request").subject("JWT Token")
                        .claim("username", authResponse.getName())
                        .claim("authorities", authResponse.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date(new Date().getTime() + 30000000))
                        .signWith(secretKey).compact();

            }

        }

        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,jwt).body(new LoginResponseDTO(HttpStatus.OK.getReasonPhrase(),jwt));
    }

}