package com.example.demo.other;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUserDetails implements UserDetails {

    private String username;
    public AppUserDetails(String username){
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         
        return Arrays.asList( new SimpleGrantedAuthority("ROLE_USER") );
    }

    @Override
    public String getPassword() {
        
        return "pass";
    }

    @Override
    public String getUsername() {
        
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { 
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { 
        return true;
    }

    @Override
    public boolean isEnabled() { 
        return true;
    }
    
}