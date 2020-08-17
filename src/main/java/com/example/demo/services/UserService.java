package com.example.demo.services;

import javax.persistence.EntityManager;

import com.example.demo.dto.UserResponseDto;
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

	@Autowired
	private EntityManager entityManager;

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
    // todo move to repo
    public UserResponseDto findById(int id){
        return entityManager.find(UserResponseDto.class, id);
       
    }
     // entityManager.merge(entity)          // update or insert based on id

    public User update(User user){
        return entityManager.merge(user);
        
    }
    public User insert(User user){
        return entityManager.merge(user);
        
    }
    public void remove(int id){
        User user = entityManager.find(User.class, id);     // fetch first
        entityManager.remove(user);
        
    }
    
}