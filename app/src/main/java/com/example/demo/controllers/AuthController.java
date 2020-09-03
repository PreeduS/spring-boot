package com.example.demo.controllers;

import javax.annotation.PostConstruct;

import com.example.demo.dto.AuthenticationJwtRequestDto;
import com.example.demo.dto.AuthenticationJwtResponseDto;
import com.example.demo.services.AppUserDetailService;
import com.example.demo.services.RestService;
import com.example.demo.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.client.RestTemplate;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    AppUserDetailService userDetailsService;
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    RestService restService;
        
    @PostConstruct
    private void init(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        restService = new RestService(restTemplate);

    }


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
            throw new BadCredentialsException("Incorrect username or password");
        }



    }
    @PostMapping("/auth/jwt2")
    public ResponseEntity<AuthenticationJwtResponseDto> authJwt2(@RequestBody AuthenticationJwtRequestDto authenticationJwtRequestDto){
        //todo add exception
        ResponseEntity<AuthenticationJwtResponseDto> response = restService.call(HttpMethod.POST, "http://localhost:10090/auth/jwt",  authenticationJwtRequestDto, AuthenticationJwtResponseDto.class);
        return response;
    }
}