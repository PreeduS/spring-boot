package com.example.demo.security.providers;

import com.example.demo.services.AppUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AppUserDetailService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        // already throws AuthenticationException
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        if(userDetails == null){
            System.out.println("Wrong username or password_");
            throw new BadCredentialsException("Wrong username or password_");
            //throw new UsernameNotFoundException("Wrong username or password_");
        }
         
        if(passwordEncoder.matches(password, userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
        }else{
            System.out.println("Wrong username or password__");
            throw new BadCredentialsException("Wrong username or password__");
            //throw new UsernameNotFoundException("Wrong username or password__");
        }


        // if the authentication fails, wrong credentials
        // throw AuthenticationException

        // if the authentication is not supported
        //return null;
    }

    // called before authenticate
    @Override
    public boolean supports(Class<?> authentication) {
        // authentication - type of Authentication

        return UsernamePasswordAuthenticationToken.class.equals(authentication);
        //return false;
    }
    
}