package com.example.demo.services;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


public class RestService {
    RestTemplate restTemplate;
    public RestService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }


    
    // ---

    public <T> ResponseEntity<T> call(HttpMethod httpMethod, String url,  Class<T> responseClass){
        return this.call(httpMethod, url, null, null, responseClass);
    }
    public <T> ResponseEntity<T> call(HttpMethod httpMethod, String url,  ParameterizedTypeReference<T> parameterizedTypeReference){
        return this.call(httpMethod, url, null, null, parameterizedTypeReference);
    }
    // ---

    public <T> ResponseEntity<T> call(HttpMethod httpMethod, String url, HttpHeaders httpHeaders,  Class<T> responseClass){
        return this.call(httpMethod, url, httpHeaders, null, responseClass);
    }
    public <T> ResponseEntity<T> call(HttpMethod httpMethod, String url, HttpHeaders httpHeaders,  ParameterizedTypeReference<T> parameterizedTypeReference){
        return this.call(httpMethod, url, httpHeaders, null, parameterizedTypeReference);
    }
    // ---

    public <T> ResponseEntity<T> call(HttpMethod httpMethod, String url, Object body,  Class<T> responseClass){
        return this.call(httpMethod, url, null, body, responseClass);
    }
    public <T> ResponseEntity<T> call(HttpMethod httpMethod, String url, Object body,  ParameterizedTypeReference<T> parameterizedTypeReference){
        return this.call(httpMethod, url, null, body, parameterizedTypeReference);
    }

    // ---
    public <T> ResponseEntity<T> call(HttpMethod httpMethod, String url, HttpHeaders httpHeaders, Object body, Class<T> responseClass){
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(body ,httpHeaders);
        return restTemplate.exchange(url,httpMethod, httpEntity, responseClass);
    }

    public <T> ResponseEntity<T> call(HttpMethod httpMethod, String url, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<T> parameterizedTypeReference){
        HttpEntity<Object> httpEntity = new HttpEntity<Object>(body ,httpHeaders);
        return restTemplate.exchange(url,httpMethod, httpEntity, parameterizedTypeReference);
    }

}
//     public <T> ResponseEntity<List<T>> call(HttpMethod httpMethod, String url, HttpHeaders httpHeaders, Object body, ParameterizedTypeReference<List<T>> parameterizedTypeReference){


// org.springframework.core.io.Resource        // ResponseEntity<Resource>  // exchange: Resource.class