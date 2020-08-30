package com.example.demo.configs;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplateHandler;

@Configuration
public class RestTemplateBuilderConfig {
    /*@Bean 
    RestTemplateBuilder restTemplate(RestTemplateBuilder builder){
        //UriTemplateHandler uriTemplateHandler = new RootUriTemplateHandler("https://jsonplaceholder.typicode.com");
        return builder; 
    }
   */
   /* RestTemplate restTemplate(RestTemplateBuilder builder){
        //UriTemplateHandler uriTemplateHandler = new RootUriTemplateHandler("https://jsonplaceholder.typicode.com");
        return builder
            //.uriTemplateHandler(uriTemplateHandler)
            //.setReadTimeout(Duration.ofMillis(1000))
            .build();
    }*/
}