package com.example.demo.configs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.example.demo.security.filters.CustomAuthenticationFilter;
import com.example.demo.security.providers.CustomAuthenticationProvider;
import com.example.demo.interceptors.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Autowired
  UserDetailsService userDetailsService;
  @Autowired
  CustomAuthenticationProvider customAuthenticationProvider;

  @Autowired
  JwtRequestFilter jwtRequestFilter;
  @Autowired
  CustomAuthenticationFilter customAuthenticationFilter;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    /*
     * auth .inMemoryAuthentication() .withUser("user") .password("pass")
     * .roles("USER") .and() .withUser("admin") .password("pass") .roles("ADMIN");
     */

    /*
     * auth .jdbcAuthentication() .withDefaultSchema() // generates default database
     * tables .dataSource(dataSource) .withUser(
     * User.withUsername("user").password("pass").roles("USER") ); //.withUser()
     */

    // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#user-schema

    /*
     * auth .jdbcAuthentication() .dataSource(dataSource)
     * .usersByUsernameQuery("SELECT username,password,enabled FROM users WHERE username = ?"
     * )
     * .authoritiesByUsernameQuery("SELECT username,authority FROM authorities WHERE username = ?"
     * );
     */

    auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

    auth.authenticationProvider(customAuthenticationProvider);
    // auth.authenticationProvider(authProvider2);

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
      http.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
      //http.addFilterAt(filter, atFilter)

      http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint(){

          @Override
          public void commence(HttpServletRequest request, HttpServletResponse response,
              AuthenticationException authException) throws IOException, ServletException {
            
                //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"AuthenticationEntryPoint: Authentication failed");
          }
      /*
          //@ExceptionHandler (value = {AccessDeniedException.class})
          @ExceptionHandler (value = {BadCredentialsException.class})
          public void commence(HttpServletRequest request, HttpServletResponse response,
          BadCredentialsException badCredentialsException) throws IOException, ServletException {
            
                //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Authentication failed, bad credentials: " + badCredentialsException.getMessage());
          }
             */ 
      });
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception { 
      return super.authenticationManager();
      // return super.authenticationManagerBean();
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