package com.example.demo.dto;

public class AuthenticationJwtRequestDto {
    private String username;
    private String password;

    public AuthenticationJwtRequestDto() {

    }
    public AuthenticationJwtRequestDto(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}