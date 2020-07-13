package com.example.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@Configuration
public class RestTempateConfig {
    
    //@Bean 
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}