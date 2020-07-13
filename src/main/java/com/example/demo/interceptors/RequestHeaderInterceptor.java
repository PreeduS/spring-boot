package com.example.demo.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.Exception.InvalidHeaderFieldException; 

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RequestHeaderInterceptor extends HandlerInterceptorAdapter{
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        
        if(StringUtils.isEmpty(request.getHeader("custom-key"))){
            //throw new Exception("Invalid request");
            throw new InvalidHeaderFieldException("Invalid request");
        }
        //response.addHeader(name, value);
        return super.preHandle(request, response, handler);
 
    }

    // intercept            // extends ClientHttpRequestInterceptor

    // postHandle(request, response, handler, modelAndView);
}