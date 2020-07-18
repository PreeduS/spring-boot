package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
    
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    void signUp(User user) throws Exception{
      
        //throw new Exception("Username is taken");
     
        
        final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());           

        user.setPassword(encryptedPassword);

        //final User createdUser = userRepository.save(user);
    }
}