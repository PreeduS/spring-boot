package com.example.user.other;
 
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

 
import com.example.user.models.User;

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
    
        List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(x ->x.getAuthority())
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

        this.authorities = authorities;

        
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

