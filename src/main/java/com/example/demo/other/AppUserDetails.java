package com.example.demo.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.models.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean enabled;
    private List<GrantedAuthority> authorities;

    public AppUserDetails(User user){
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        /*this.authorities = Arrays.stream(user.getRoles().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());*/
        String[] roles = {"USER"};
        this.authorities = Arrays.stream( roles )
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

        
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         
        return authorities;
    }

    @Override
    public String getPassword() { 
        return password;
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
        return enabled;
    }


    
}


/*


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

*/