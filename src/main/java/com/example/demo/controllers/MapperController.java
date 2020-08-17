package com.example.demo.controllers;

import com.example.demo.pojo.MapperPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapperController {

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/mapper/test")
    public String test() {
        String jsonSource = "{\"title\":\"test title\"}";
        try {
            JsonNode node = parse(jsonSource);
            String title = node.get("title").asText();

            MapperPojo mapperPojo = fromJson(node, MapperPojo.class);
            System.out.println("mapperPojo title:  " + mapperPojo.getTitle());

            return title;


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public JsonNode parse(String src) throws JsonMappingException, JsonProcessingException {
        return objectMapper.readTree(src);
    }
    public <T> T fromJson(JsonNode node, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.treeToValue(node, valueType);
    }


        
}