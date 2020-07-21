package com.example.demo.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFilter implements Filter { 

    @Autowired 
    AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
      
        HttpServletRequest http = (HttpServletRequest) request;         // or implements HttpFilter
        String authorization = http.getHeader("Authorization");

        Authentication result = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken("username", authorization)
        );
        if(result.isAuthenticated()){
            SecurityContextHolder.getContext().setAuthentication(result);
            chain.doFilter(request, response);
        }
        
    }
    
}


// 40:00