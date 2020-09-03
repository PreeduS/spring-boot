package com.example.demo.interceptors;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.services.AppUserDetailService;
import com.example.demo.services.UserService;
import com.example.demo.utils.HttpResponseUtil;
import com.example.demo.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    AppUserDetailService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        String username = null;
        String jwt = null;
        String prefix = "Bearer ";

        if(authorizationHeader != null && authorizationHeader.startsWith(prefix)){
            jwt = authorizationHeader.substring(prefix.length());

            username = jwtUtil.extractUsername(jwt);

            
        }
 
        //if(username != null ){
        if(username != null && jwt != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt, userDetails)){
                // default
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else{
                HttpResponseUtil.writeExceptionToResponse(response, "Invalid token", request.getRequestURL().toString());
            }



        }else{
            HttpResponseUtil.writeExceptionToResponse(response, "Invalid token", request.getRequestURL().toString());
        }
        filterChain.doFilter(request, response);
        
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean result = request.getServletPath().equals("/auth/jwt2") || request.getServletPath().equals("/auth/jwt") || request.getServletPath().equals("/login") || request.getServletPath().equals("/graphql");
        return result;
		//return super.shouldNotFilter(request);
    }
    

    
}