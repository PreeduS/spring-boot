package com.example.user.utils;

import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.common.base.Charsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class FileUtil {

    public String readFileAsString(String path) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream resource = classLoader.getResourceAsStream(path);
        try {
            InputStreamReader reader = new InputStreamReader(resource, Charsets.UTF_8);
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }
    public String readResourceAsString(Resource resource) {
        try {
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), Charsets.UTF_8);
            return FileCopyUtils.copyToString(reader);
        }   catch (IOException e) { 
            e.printStackTrace();
        }

        return null; 
    }
}

// check: ResourceLoader resourceLoader; resourceLoader.getResource(path)
// https://www.baeldung.com/spring-classpath-file-access