package com.example.user.controllers;

import com.example.user.dto.AuthenticationJwtRequestDto;
import com.example.user.dto.AuthenticationJwtResponseDto;
import com.example.user.services.AppUserDetailService;
import com.example.user.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AppUserDetailService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    
    
    @PostMapping("/auth/jwt")
    public ResponseEntity<AuthenticationJwtResponseDto> authJwt(@RequestBody AuthenticationJwtRequestDto authenticationJwtRequestDto){
        //try{

            //authenticationManager.authenticate(
            //    new UsernamePasswordAuthenticationToken(authenticationJwtRequestDto.getUsername(), authenticationJwtRequestDto.getPassword())
            //);
       // }catch(BadCredentialsException e){
            //throw new BadCredentialsException("Incorrect username or password");
       // }

        try{

            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationJwtRequestDto.getUsername());
            String jwtToken = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok( 
                new AuthenticationJwtResponseDto(jwtToken) 
            );
        }catch(Exception e){
            throw new RuntimeException(e);
        }



    } 
}