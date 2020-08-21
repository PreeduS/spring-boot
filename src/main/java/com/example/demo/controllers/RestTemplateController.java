package com.example.demo.controllers;

import java.util.List;

import javax.annotation.PostConstruct;

import com.example.demo.interceptors.RestTemplateInterceptor;
import com.example.demo.services.RestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
public class RestTemplateController {
        //RestTemplate restTemplate;// = new RestTemplate();
        @Autowired
        RestTemplateBuilder restTemplateBuilder;// = new RestTemplate();
    
        RestTemplate restTemplate;

        
        RestService restService;
        
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

            restService = new RestService(restTemplate);
    
        }


        @GetMapping("/rest/post")
        public Post post( ) {
            Post post = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1",Post.class);
            System.out.println(post);
            return post;
    
        } 
        @GetMapping("/rest/posts")
        public Post[] posts( ) {
        //public ParameterizedTypeReference<List<Post>> posts( ) {
            Post[] posts = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts",Post[].class);
            //ParameterizedTypeReference<List<Post>> posts = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts",  new ParameterizedTypeReference<List<Post>>(){} );
        
            return posts;
    
            // getForEntity returns the ResponseEntity object
            // getForObject returns the object directly
    
        } 


        @GetMapping("/rest/posts2")
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
        @GetMapping("/rest/posts3")
        public ResponseEntity<Post[]> posts3( ) {
            HttpHeaders headers = new HttpHeaders();
    
            Object body = new Post(10);
            HttpEntity<Object> httpEntity = new HttpEntity<Object>(body ,headers);
            System.out.println(httpEntity.getBody());    
            ResponseEntity<Post[]> response = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts",HttpMethod.GET, httpEntity, Post[].class);
     
    
            return response;
    
        } 

        @GetMapping("/rest/posts4")
        public ResponseEntity<Post[]>  posts4(){
            UriComponentsBuilder urlBuilder = UriComponentsBuilder
                .fromHttpUrl("https://jsonplaceholder.typicode.com")
                .pathSegment("posts");
            
            UriComponents uriComponents = urlBuilder.build();
            String uri = uriComponents.toUriString();
            
            System.out.println("uri: " + uri);

            
            return restService.call(HttpMethod.GET, uri,  null, Post[].class);
            
            // UriComponents uriComponents = UriComponentsBuilder
            //    .newInstance().scheme("https").host("jsonplaceholder.typicode.com").path("/posts").build().encode();
        


        }

}