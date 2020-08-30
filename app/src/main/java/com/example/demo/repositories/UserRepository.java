package com.example.demo.repositories;

import java.util.Optional;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// public interface UserRepository extends JpaRepository<User, Integer>{
public interface UserRepository extends JpaRepository<User, Integer>, CustomUserRepository{
    Optional<User> findByUsername(String username); // skip password
        
    //@Override
    //@SuppressWarnings("unchecked")
    /*
    default Optional<User> findByUsername(String username){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("pass");
        return Optional.ofNullable(user);
    }
    */
    
}