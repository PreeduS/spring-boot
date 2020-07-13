package com.example.demo.controllers;

import java.util.List;

import javax.annotation.PostConstruct;

import com.example.demo.interceptors.RestTemplateInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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



    //RestTemplate restTemplate;// = new RestTemplate();
    @Autowired
    RestTemplateBuilder restTemplateBuilder;// = new RestTemplate();

    RestTemplate restTemplate;
    
    @PostConstruct
    private void init(){
 
        restTemplate = restTemplateBuilder.build();

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();       
        // new ArrayList<>();
        /*if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }*/

        interceptors.add(new RestTemplateInterceptor());

        
        restTemplate.setInterceptors(
           interceptors
        );

    }

    @GetMapping("/test/post")
    public Post post( ) {
        Post post = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1",Post.class);
        System.out.println(post);
        return post;

    } 
    @GetMapping("/test/posts")
    public Post[] posts( ) {
    //public ParameterizedTypeReference<List<Post>> posts( ) {
        Post[] posts = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts",Post[].class);
        //ParameterizedTypeReference<List<Post>> posts = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts",  new ParameterizedTypeReference<List<Post>>(){} );
    
        return posts;

        // getForEntity returns the ResponseEntity object
        // getForObject returns the object directly

    } 
    @GetMapping("/test/posts2")
    public ResponseEntity<Post[]> posts2( ) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("headerName", "headerValue");
        headers.setContentType(MediaType.APPLICATION_JSON);
      
        HttpEntity<Post[]> httpEntity = new HttpEntity<Post[]>(headers);
        
        ResponseEntity<Post[]> response = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts",HttpMethod.GET, httpEntity, Post[].class);
 
        // HttpStatus statusCode = response.getStatusCode();
        // HttpHeaders statusCode = response.getHeaders();
        
        return response;

    } 
    @GetMapping("/test/posts3")
    public ResponseEntity<Post[]> posts3( ) {
        HttpHeaders headers = new HttpHeaders();

        Object body = new Post(10);
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(body ,headers);
        System.out.println(httpEntity.getBody());    
        ResponseEntity<Post[]> response = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts",HttpMethod.GET, httpEntity, Post[].class);
 

        return response;

    } 


    
}