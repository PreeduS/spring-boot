package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${custom.value}")
    private String value;

    @Autowired
    Environment env;

    @GetMapping("/test/interceptor")
    public ResponseEntity<String> interceptor(){
        return ResponseEntity.ok("interceptor");
    }
    @GetMapping("/test/value")
    public ResponseEntity<String> testValue(){
        return ResponseEntity.ok("value = " + value);
    }
    @GetMapping("/test/value2")
    public ResponseEntity<String> testValue(@Value("${custom.value}") String value ){
        System.out.println(env.getDefaultProfiles());
        System.out.println("custom.value = " + env.getProperty("custom.value"));
        return ResponseEntity.ok("value2 = " + value + "<br />" + env.toString());
    }
    
}