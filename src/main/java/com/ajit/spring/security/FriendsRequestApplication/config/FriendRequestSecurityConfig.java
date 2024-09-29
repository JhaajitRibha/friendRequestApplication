package com.ajit.spring.security.FriendsRequestApplication.config;

import com.ajit.spring.security.FriendsRequestApplication.exceptionHandler.CustomAccessDeniedHandler;
import com.ajit.spring.security.FriendsRequestApplication.exceptionHandler.CustomBasicAuthenticationEntryPoint;
import com.ajit.spring.security.FriendsRequestApplication.filter.JWTTokenGenerationFilter;
import com.ajit.spring.security.FriendsRequestApplication.filter.JWTTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class FriendRequestSecurityConfig {


    @Autowired
    private  FriendRequestUsernamePasswordAuthenticationProvider customAuthProvider;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler).ignoringRequestMatchers( "/rest/v1/register", "/rest/v1/apiLogin"))
                        .addFilterAfter(new JWTTokenGenerationFilter(), BasicAuthenticationFilter.class)
                                .addFilterAfter(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);


                http.authorizeHttpRequests(request->request
                        .requestMatchers("/rest/v1/account").hasRole("ADMIN")
                        .requestMatchers("/rest/v1/balance").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/rest/v1/cards").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/rest/v1/loans").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/rest/v1/testing").authenticated()
                        .requestMatchers("/rest/v1/contacts","/rest/v1/notice","/rest/v1/welcome","/rest/v1/login",
                      "/login/**",
                       "/css/**","/error",
                       "/rest/v1/register","/rest/v1/apiLogin","/rest/v1/user").permitAll());


        http.formLogin(withDefaults() );


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

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){

        FriendRequestUsernamePasswordAuthenticationProvider friendRequestUsernamePasswordAuthenticationProvider =
                new FriendRequestUsernamePasswordAuthenticationProvider((FriendsRequestUserDetailsService) userDetailsService,passwordEncoder);
        ProviderManager providerManager = new ProviderManager(friendRequestUsernamePasswordAuthenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

}

