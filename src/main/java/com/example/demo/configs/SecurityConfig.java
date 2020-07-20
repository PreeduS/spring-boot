package com.example.demo.configs;

import javax.sql.DataSource;

import com.example.demo.interceptors.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;    

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;


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


     
        auth
          .userDetailsService(userDetailsService)
          .passwordEncoder(getPasswordEncoder());
        

        //// auth.authenticationProvider(authProvider);       // todo check

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
      http
      .csrf().disable()
      .authorizeRequests()
      .antMatchers("/protected/admin").hasRole("ADMIN")
      .antMatchers("/protected/user").hasAnyRole("USER","ADMIN")                // hasAnyAuthority(authorities) // manually handle role prefix
      .antMatchers("/auth/jwt").permitAll()        
        //.antMatchers("/","static/css","static/js").permitAll()                // .antMatchers("/**")     // .antMatchers(HttpMethod.GET, "path")  
      .antMatchers("/").permitAll()
      //.anyRequest().authenticated() //any request needs to be authenticated
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);      // jwt, don't create session
      //.and()
      //.formLogin();
      // .loginPage("/sign-in")

      http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception { 
      return super.authenticationManager();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        // temp
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
  
      return new BCryptPasswordEncoder();
    }
    
}