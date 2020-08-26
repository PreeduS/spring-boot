package com.example.demo.configs;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.example.demo.security.filters.CustomAuthenticationFilter;
import com.example.demo.security.providers.CustomAuthenticationProvider;
import com.example.demo.services.AppUserDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.demo.interceptors.JwtRequestFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
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
  AppUserDetailService appUserDetailService;
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

    
        auth
        .jdbcAuthentication()
        .dataSource(dataSource);
        //.passwordEncoder(getPasswordEncoder())
        //.withUser("user").password( getPasswordEncoder().encode("pass") ).roles("USER")
        //.and()
        //.withUser("admin").password( getPasswordEncoder().encode("pass") ).roles("ADMIN") ;
    

   // auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    auth.userDetailsService(appUserDetailService);

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
      .antMatchers("/login").anonymous()
      .antMatchers("/user-test").anonymous()
      .antMatchers("/user-test2").anonymous()
      .antMatchers("/test/**").permitAll()
      .antMatchers("/mapper/**").permitAll()
      .antMatchers("/").permitAll()
      .anyRequest().authenticated() //any request needs to be authenticated
      .and()
      .sessionManagement()
      //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)      // jwt, don't create session
      .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)     


      .and()
      .formLogin().loginPage("/login").permitAll();
      /*
      .and()
      .formLogin()
      .loginPage("/sign-in")
      .defaultSuccessUrl("/path", true)      // successForwardUrl
      .and()
      .rememberMe()                         // "remember-me" form checkbox name           // creates rememberMe cookie (username + expiration time + md5 signature)
        .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21))
        .key("secret")                      // key for md5 signature in the rememberMe cookie
        //.tokenRepository()                // custom db, default in memory

      .and()
      .logout()
        .logoutUrl("/logout")
        // logoutRequestMatcher(...)
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .deleteCookies("JSESESSIONID", "remember-me")
        .logoutSuccessUrl("/login");
          
      */
      
      //http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
      //http.addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
      http.addFilterAfter(customAuthenticationFilter, ExceptionTranslationFilter.class);
      //http.addFilterAt(filter, atFilter)

   /*
      http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) ->{
        response.setStatus(403);
        response.getWriter().write("Forbidden: accessDeniedHandler " + accessDeniedException.getMessage());
      });*/

      http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint(){

          @Override
          public void commence(HttpServletRequest request, HttpServletResponse response,
              AuthenticationException authException) throws IOException, ServletException {
            

                //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"AuthenticationEntryPoint: Authentication failed");
                
                //response.getOutputStream().println("{ \"error\": \"" + "test " + authException.getMessage() + "\" }");

                Map<String, Object> data = new HashMap<>();
                data.put("timestamp", new Date());
                data.put("status",HttpStatus.FORBIDDEN.value());
                data.put("message", authException.getMessage());
                data.put("path", request.getRequestURL().toString());

                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                OutputStream out = response.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(out, data);
                out.flush();
               
          }
       /*   
          @ExceptionHandler (value = {BadCredentialsException.class})
          public void commence(HttpServletRequest request, HttpServletResponse response,
            BadCredentialsException authException) throws IOException, ServletException {
            
                //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"AuthenticationEntryPoint: Authentication failed");
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().println("{ \"error\": \"" + "BadCredentialsException " + authException.getMessage() + "\" }");
                //response.getWriter().write(s);
               
          }
      
     
              */
      });
    }

    @Override
    @Bean
    // disables in-memory AuthenticationManager from AuthenticationManagerConfiguration
    protected AuthenticationManager authenticationManager() throws Exception { 
      return super.authenticationManager();
      // return super.authenticationManagerBean();

      // Spring Security ships only one real AuthenticationManager implementation: 
      // org.springframework.security.authentication.ProviderManager
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
      // todo set bean based on profile
      return NoOpPasswordEncoder.getInstance();
      /*
      // return PasswordEncoderFactories.createDelegatingPasswordEncoder();
      String encodingId = "bcrypt";

      Map<String, PasswordEncoder> encoders = new HashMap<>();
      encoders.put(encodingId, new BCryptPasswordEncoder());
      return new DelegatingPasswordEncoder(encodingId, encoders);*/
    }
/*
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
  
      return new BCryptPasswordEncoder();

      
    }
  */  
}