package com.example.demo.interceptors;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.services.AppUserDetailService;
import com.example.demo.services.UserService;
import com.example.demo.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        if(username != null &&  userService.getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt, userDetails)){
                 //Collection<?> z = userDetails.getAuthorities();

                // default
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                userService.setAuthentication(usernamePasswordAuthenticationToken);

            }


            //catch io.jsonwebtoken.SignatureException:


        }
        filterChain.doFilter(request, response);
        
    }
    
}