package com.example.demo.services;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {
    
   // @Autowired
   // BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserRepository userRepository;

    void signUp(User user) throws Exception{
      
        //throw new Exception("Username is taken");
     
        
      //  final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());           

      //  user.setPassword(encryptedPassword);

        //final User createdUser = userRepository.save(user);
    }

    public Authentication getAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
        // if (!(authentication instanceof AnonymousAuthenticationToken)) {

    }


    public String getCurrentUsername(){
        return getAuthentication().getName();
    }
    public Boolean isAuthenticated(){
        return getAuthentication().isAuthenticated();
    }
    public void setAuthentication(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);;
    }

    public void logout(){
        Authentication auth = getAuthentication();

        if(auth != null){
           //new SecurityContextLogoutHandler().logout(request, response, auth);
        }

    }
}