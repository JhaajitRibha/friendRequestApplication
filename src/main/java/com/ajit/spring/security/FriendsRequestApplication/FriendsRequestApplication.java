package com.ajit.spring.security.FriendsRequestApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class FriendsRequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendsRequestApplication.class, args);
	}

}
