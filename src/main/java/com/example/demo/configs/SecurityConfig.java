package com.example.demo.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;    

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        /*
        auth
        .inMemoryAuthentication()
        .withUser("user")
        .password("pass")
        .roles("USER")
        .and()
        .withUser("admin")
        .password("pass")
        .roles("ADMIN");
        */

        /*
        auth
        .jdbcAuthentication()
        .withDefaultSchema()                      // generates default database tables
        .dataSource(dataSource)
        .withUser(
            User.withUsername("user").password("pass").roles("USER")
        );
        //.withUser()
        */



        // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#user-schema


        /*auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("SELECT username,password,enabled FROM users WHERE username = ?")
        .authoritiesByUsernameQuery("SELECT username,authority FROM authorities WHERE username = ?");
        */



        auth.userDetailsService(userDetailsService);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
      http
      .authorizeRequests()
      
      .antMatchers("/auth/admin").hasRole("ADMIN")
      .antMatchers("/auth/user").hasAnyRole("USER","ADMIN")
        //.antMatchers("/","static/css","static/js").permitAll()                //.antMatchers("/**")
      .antMatchers("/").permitAll()
      .and()
      .formLogin();
 
    }



    @Bean
    public PasswordEncoder getPasswordEncoder(){
        // temp
        return NoOpPasswordEncoder.getInstance();
    }
    
}