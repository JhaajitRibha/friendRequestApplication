package com.ajit.spring.security.FriendsRequestApplication.exceptionHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper jsonMapper= new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setHeader("friend-request-access-denied","access not granted,my friend !");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss");
        String formattedDate = dateFormatter.format(new Date());

        String accessDeniedMessage = (accessDeniedException!=null && accessDeniedException.getMessage()!=null)?accessDeniedException.getMessage()+", my friend":"Authentication failed , my friend !!";
        Map<String,Object> responseBody = new HashMap<>();
        responseBody.put("timestamp",formattedDate);
        responseBody.put("status",HttpStatus.FORBIDDEN.value());
        responseBody.put("error","ACCESS_DENIED");
        responseBody.put("message",accessDeniedMessage);
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

