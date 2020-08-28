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
        System.out.println("authorizationHeader : "+authorizationHeader);
        if(authorizationHeader != null && authorizationHeader.startsWith(prefix)){
            jwt = authorizationHeader.substring(prefix.length());
            username = jwtUtil.extractUsername(jwt);
            
        }

        //if(username != null &&  userService.getAuthentication() == null){
        if(username != null ){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt, userDetails)){
                 //Collection<?> z = userDetails.getAuthorities();
                 System.out.println("--------------------------is valid jwt" + username);

                // default
                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                
                    // ??
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //userService.setAuthentication(usernamePasswordAuthenticationToken);
System.out.println("usernamePasswordAuthenticationToken isAuthenticated "+ usernamePasswordAuthenticationToken.isAuthenticated());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else{
                System.out.println("--------------------------not valid jwt" + username);
            }


            //catch io.jsonwebtoken.SignatureException:


        }
        filterChain.doFilter(request, response);
        
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        boolean result =  request.getServletPath().equals("/login") || request.getServletPath().equals("/graphql");
        return result;
		//return super.shouldNotFilter(request);
	}
    
}