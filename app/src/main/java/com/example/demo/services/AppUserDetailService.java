package com.example.demo.services;

import java.util.Optional;

import com.example.demo.models.User;
import com.example.demo.other.AppUserDetails;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(()-> new UsernameNotFoundException("Not Found"));
        //return user.map(AppUserDetails::new).get();
 
        return new AppUserDetails(user.get());
     
        
        //return new AppUserDetails(username);
    }
    
}

