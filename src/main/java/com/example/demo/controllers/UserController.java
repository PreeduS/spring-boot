package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.Authorities;
import com.example.demo.models.User;
import com.example.demo.repositories.AuthoritiesRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthoritiesRepository authoritiesRepository;
    @Autowired
    UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<Optional<User>> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userRepository.findByUsername(username));
    }
    @GetMapping("/current-user/{username}")
    public ResponseEntity<String> getCurrentUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.getCurrentUsername());
    }

    @GetMapping("/authorities/{username}")
    public ResponseEntity<List<Authorities>> findAuthoritiesByUsername(@PathVariable String username){
        return ResponseEntity.ok(authoritiesRepository.findAllByUserUsername(username));
    }
}