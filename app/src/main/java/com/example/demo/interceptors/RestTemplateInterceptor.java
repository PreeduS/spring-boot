package com.example.demo.interceptors;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;

public class RestTemplateInterceptor implements ClientHttpRequestInterceptor {
    private HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        String value = "test";
        byte[] encodedValue = Base64Utils.encode(value.getBytes());
        headers.set("custom-header", new String(encodedValue));
        headers.set("custom-header2", new String(encodedValue));
        // headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return headers;
    }
    
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        request.getHeaders().addAll(getHeaders());
        return execution.execute(request, body);
    }
    
}