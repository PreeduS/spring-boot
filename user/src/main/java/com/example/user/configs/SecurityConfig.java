package com.example.user.configs;

import com.example.user.services.AppUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AppUserDetailService appUserDetailService;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  
        auth.userDetailsService(appUserDetailService);

  
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
      http
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/auth/jwt").permitAll()        
      .anyRequest().permitAll()  
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);      // jwt, don't create session
 
    

    
    }

    @Override
    @Bean
    // disables in-memory AuthenticationManager from AuthenticationManagerConfiguration
    protected AuthenticationManager authenticationManager() throws Exception { 
      return super.authenticationManager();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){

      return NoOpPasswordEncoder.getInstance();

    }

}