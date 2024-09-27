package com.ajit.spring.security.FriendsRequestApplication.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper jsonMapper= new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        response.setHeader("friendRequest-app-error-reason", "Authentication is failed , my friend !!");
//        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("friend-request-authentication","authentication failed , my friend !!");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss");
        String formattedDate = dateFormatter.format(new Date());

        String authenticationMessage = (authException!=null && authException.getMessage()!=null)?authException.getMessage()+", my friend":"Authentication failed , my friend !!";
        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("timestamp",formattedDate);
        responseBody.put("status",HttpStatus.UNAUTHORIZED.value());
        responseBody.put("error","UNAUTHORIZED");
        responseBody.put("message",authenticationMessage);
        responseBody.put("path",request.getRequestURI());

        String acceptHeader = request.getHeader("Accept");

        if(acceptHeader!=null && acceptHeader.contains("application/xml")){
            response.setContentType("application/xml");
            String xmlResponse = xmlMapper.writeValueAsString(responseBody);
            response.getWriter().write(xmlResponse);

        }else{

            response.setContentType("application/json");
            String jsonResponse  = jsonMapper.writeValueAsString(responseBody);
            response.getWriter().write(jsonResponse);

        }


    }
}
