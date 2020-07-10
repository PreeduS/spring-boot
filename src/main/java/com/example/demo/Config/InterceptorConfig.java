package com.example.demo.Config;

import com.example.demo.Interceptor.RequestHeaderInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
    @Autowired
    RequestHeaderInterceptor requestHeaderInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){

        //registry.addInterceptor(requestHeaderInterceptor); // all paths
        registry.addInterceptor(requestHeaderInterceptor).addPathPatterns("/test/interceptor");         // "/test/*"
    }

}