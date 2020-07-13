package com.example.demo.repositories;

import java.util.Optional;
import com.example.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByUsername(String username);
}