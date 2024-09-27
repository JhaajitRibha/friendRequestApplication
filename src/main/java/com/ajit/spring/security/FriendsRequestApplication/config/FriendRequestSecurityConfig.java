package com.ajit.spring.security.FriendsRequestApplication.config;

import com.ajit.spring.security.FriendsRequestApplication.exceptionHandler.CustomAccessDeniedHandler;
import com.ajit.spring.security.FriendsRequestApplication.exceptionHandler.CustomBasicAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class FriendRequestSecurityConfig {


    @Autowired
    private  FriendRequestUsernamePasswordAuthenticationProvider customAuthProvider;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
//        http.requiresChannel(rcc->rcc.anyRequest().requiresSecure())
                http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests((requests) -> requests.
               requestMatchers("/rest/v1/contacts","/rest/v1/notice","/rest/v1/welcome","/rest/v1/login","/login/**","/css/**","/error","/rest/v1/register").permitAll()
             .requestMatchers("/rest/v1/account","/rest/v1/balance","/rest/v1/cards","/rest/v1/loans","/rest/v1/testing").authenticated());
        http.formLogin(flc->flc.
                loginPage("/rest/v1/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/rest/v1/welcome",true)
                .failureUrl("/login?error=true")
                .permitAll()    );

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/rest/v1/welcome")
                .permitAll());

        http.httpBasic(withDefaults());

        http.authenticationProvider(customAuthProvider);
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource datasource){
////        UserDetails ajit = User.withUsername("ajit")
////                .password("{noop}ajit@12@34")
////                .authorities("read")
////                .build();
////
////        UserDetails samar = User.withUsername("samar")
////                .password("{bcrypt}$2a$12$PFPjcFWj01Q.g6Eo.cVtA.YTb3jc0cjQ4YlJovsdpevup8sJENcEG")
////                .authorities("admin")
////                .build();
////
////        return new InMemoryUserDetailsManager(ajit,samar);
//
//        return new JdbcUserDetailsManager(datasource);
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}

