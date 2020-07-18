package com.example.demo.dto;

public class AuthenticationJwtResponseDto {
    private final String jwt;

    public AuthenticationJwtResponseDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}