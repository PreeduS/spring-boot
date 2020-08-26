package com.example.demo.utils;

import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class FileUtil {

    public String readResourceAsString(Resource resource) {
        try {
            InputStreamReader reader = new InputStreamReader(resource.getInputStream(), "UTF-8");
            return FileCopyUtils.copyToString(reader);
        }   catch (IOException e) { 
            e.printStackTrace();
        }

        return null; 
    }
}