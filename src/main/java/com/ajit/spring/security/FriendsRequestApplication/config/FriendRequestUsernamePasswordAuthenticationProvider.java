package com.ajit.spring.security.FriendsRequestApplication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendRequestUsernamePasswordAuthenticationProvider implements AuthenticationProvider {


    private final FriendsRequestUserDetailsService friendsRequestUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        UserDetails userDetails = friendsRequestUserDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(pwd,userDetails.getPassword())){

            return new UsernamePasswordAuthenticationToken(username,pwd,userDetails.getAuthorities());
        }else{
            throw new BadCredentialsException("invalid Password !");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
