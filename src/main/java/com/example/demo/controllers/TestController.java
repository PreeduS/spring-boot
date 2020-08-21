package com.example.demo.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import com.example.demo.dto.TestEntityDto;
import com.example.demo.dto.TestEntityTargetDto;
import com.example.demo.enums.AppRoles;
import com.example.demo.enums.UserPermissions;
import com.example.demo.interceptors.RestTemplateInterceptor;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${custom.value}")
    private String value;

    @Autowired
    Environment env;

    @GetMapping("/test/enum")
    public ResponseEntity<String> getEnum() {
        // System.out.println(AppRoles.ADMIN.name());
        // System.out.println(AppRoles.USER.name());
        System.out.println(UserPermissions.USER_READ);

        return ResponseEntity.ok("enum");
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {

        return ResponseEntity.ok("<form  method=\"post\" action=\"/login\">"
                + "<input type=\"text\" name=\"username\"  required autofocus>"
                + "<input type=\"password\" name=\"password\"  required >" + "<button  type=\"submit\">Sign in</button>"
                + " </form>");
    }

    @GetMapping("/test/interceptor")
    public ResponseEntity<String> interceptor() {
        return ResponseEntity.ok("interceptor");
    }

    @GetMapping("/test/value")
    public ResponseEntity<String> testValue() {
        return ResponseEntity.ok("value = " + value);
    }

    @GetMapping("/test/value2")
    public ResponseEntity<String> testValue(@Value("${custom.value}") String value) {
        System.out.println(env.getDefaultProfiles());
        System.out.println("custom.value = " + env.getProperty("custom.value"));
        return ResponseEntity.ok("value2 = " + value + "<br />" + env.toString());
    }

    // --
    @GetMapping("/test/utils")
    public void testUtils() {
        List<String> properties = Arrays.asList("p", "p2", "p3","null");
        TestEntityDto source = new TestEntityDto();
        source.setP("test");
        source.setP2(10);
        source.setP3(Arrays.asList("A", "B"));
        TestEntityTargetDto target = new TestEntityTargetDto();

        try{
            for(String property: properties){
                boolean isReadableSource = PropertyUtils.isReadable(source, property);
                boolean isWriteableSource = PropertyUtils.isWriteable(source, property);
                boolean isReadableTarget = PropertyUtils.isReadable(target, property);
                boolean isWriteableTarget = PropertyUtils.isWriteable(target, property);
                String type = PropertyUtils.getPropertyType(source, property).getSimpleName();
                if( isReadableSource ){
                    System.out.println(property + " is isReadableSource" );
                }
                if( isWriteableSource ){
                    System.out.println(property + " is isWriteableSource" );
                }
                if( isReadableTarget ){
                    System.out.println(property + " is isReadableTarget" );
                }
                if( isWriteableTarget ){
                    System.out.println(property + " is isWriteableTarget" );
                }
                System.out.println("type: " + type);
        

                if( isReadableSource && isWriteableTarget){
                    Object value = PropertyUtils.getProperty(source,property);
                    PropertyUtils.setProperty(target, property, value);
                }
                System.out.println("equals: " + property + " - " + ObjectUtils.nullSafeEquals(PropertyUtils.getProperty(target,property), PropertyUtils.getProperty(source,property)));
                
                
    
                System.out.println("\n" );
            }
        }catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException e){
            System.out.println(e);
        }
    }



    
}