package com.example.demo.dto;

public class AuthenticationJwtResponseDto {
    private String jwt;

    public AuthenticationJwtResponseDto() {
        
    }
    public AuthenticationJwtResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}