package com.example.demo.controllers;

import com.example.demo.dto.AuthenticationJwtRequestDto;
import com.example.demo.dto.AuthenticationJwtResponseDto;
import com.example.demo.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/protected/user")
    public String protectedUser() {
        return "protected/user";
    } 
    @GetMapping("/protected/admin")
    public String protectedAdmin() {
        return "protected/admin";
    } 
    @GetMapping("/protected/admin2")
    // hasRole, hasAnyRole, hasAuthority, hasAnyAuthority
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public String protectedAdmin2() {
        return "protected/admin2";
    } 
    @PostMapping("/auth/jwt")
    public ResponseEntity<AuthenticationJwtResponseDto> authJwt(@RequestBody AuthenticationJwtRequestDto authenticationJwtRequestDto) throws Exception{
        //try{

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationJwtRequestDto.getUsername(), authenticationJwtRequestDto.getPassword())
            );
       // }catch(BadCredentialsException e){
            //throw new BadCredentialsException("Incorrect username or password");
       // }


        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationJwtRequestDto.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok( 
            new AuthenticationJwtResponseDto(jwtToken) 
        );

    } 
}