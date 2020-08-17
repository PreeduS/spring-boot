package com.example.demo.repositories;

import java.util.Optional;
import com.example.demo.models.User;

public class CustomUserRepositoryImpl implements CustomUserRepository {

    @Override
    public String test() {
      
        return "test";
    }
    
    /*
    //@Override
    Optional<User> findByUsername(String username){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("pass");
        return Optional.ofNullable(user);
    }
    */
}

// extend
// the suffix Impl is what actually tell Spring Data JPA that this is a custom implementation of the existing repository