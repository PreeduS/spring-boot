package com.example.demo.security.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

       
        String authorization = request.getHeader("Authorization");

        // Authorization: Basic <credentials>
        // iplement Authentication instead of UsernamePasswordAuthenticationToken
        Authentication result = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken("admin", authorization));
        if (result.isAuthenticated()) {
            // filters set the context
            SecurityContextHolder.getContext().setAuthentication(result);
            chain.doFilter(request, response);
        }

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //return !request.getServletPath().equals("/login");
		return super.shouldNotFilter(request);
	}
 
    
}


 /* 

@Component
public class CustomAuthenticationFilter implements Filter {     

    @Autowired 
    AuthenticationManager authenticationManager;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
      
        HttpServletRequest http = (HttpServletRequest) request;         // or implements HttpFilter
        String authorization = http.getHeader("Authorization");

        // Authorization: Basic <credentials>
        Authentication result = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken("admin", authorization)
        );
        if(result.isAuthenticated()){
            // filters set the context
            SecurityContextHolder.getContext().setAuthentication(result);
            chain.doFilter(request, response);
        }
        
    }
    
}


 */