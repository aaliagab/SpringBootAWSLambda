package com.spring.aws.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilters implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String token = httpServletRequest.getHeader("token");

        //ejemplo para soloaceptar el token ABCde123
        if(token==null || token.isEmpty() || !token.equalsIgnoreCase("ABCde123")){
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("Error","El token es invalido");

            httpServletResponse.setStatus(401);
            httpServletResponse.setContentType("Application/json");
            httpServletResponse.getOutputStream().write(objectMapper.writeValueAsBytes(objectNode));
            httpServletResponse.getOutputStream().flush();
        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }
}
