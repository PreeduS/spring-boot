package com.example.demo.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsRegister implements WebMvcConfigurer{
    @Autowired
    RequestHeaderInterceptor requestHeaderInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){

        //registry.addInterceptor(requestHeaderInterceptor); // all paths
        registry.addInterceptor(requestHeaderInterceptor).addPathPatterns("/test/interceptor");         // "/test/*"
    }

}