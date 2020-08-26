package com.example.demo.controllers;

import javax.annotation.PostConstruct;

import com.example.demo.dto.MapperDto;
import com.example.demo.dto.TestEntityDto;
import com.example.demo.dto.TestEntityTargetDto;
import com.example.demo.utils.MapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MapperController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MapperUtils mapperUtils;

    @PostConstruct
    public void init(){
        // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // mapperUtils = new MapperUtils(objectMapper);
        mapperUtils.configure(objectMapper -> objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));
    }

    @GetMapping("/mapper/test")
    public String test() {
        String jsonSource = "{\"title\":\"test title\"}";
        try {
            JsonNode node = mapperUtils.parse(jsonSource);
            String title = node.get("title").asText();

            MapperDto mapperDto = mapperUtils.fromJson(node, MapperDto.class);
            System.out.println("mapperDto title:  " + mapperDto.getTitle());
            
            
            return title;


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
    @GetMapping("/mapper/test2")
    public String test2() {
        MapperDto mapperDto = new MapperDto();
        mapperDto.setTitle("title");
        mapperDto.setAuthor("author");
        JsonNode node = mapperUtils.toJson(mapperDto);

        String result = node.get("title") + ", " + node.get("author");
        System.out.println(result);
        // assertEquals(result.asText(), "value")
        return result;
    }
    @GetMapping("/mapper/test3")
    public void test3() {
       TestEntityDto source  = new TestEntityDto();
        
       MapperDto mapper = new MapperDto();
       mapper.setTitle("mapper title");
       source.setP("test");
       source.setP2(10);
       source.setMapper(mapper);

       // TestEntityTargetDto target = objectMapper.convertValue(source, TestEntityTargetDto.class);
       TestEntityTargetDto target = mapperUtils.mapToObject(source, TestEntityTargetDto.class);

       System.out.println(target.getP() + ", " + target.getP2());
    }



        
}


// https://www.youtube.com/playlist?list=PLAuGQNR28pW4dOc5uytMdzcQ4-TCJFUN4
// Parsing Json in Java Tutorial Series     # done


// ch: jackson

// temp
/*
    public static <T> T clone(Object object, Class<T> clazz) {

        ObjectMapper objectMapper = new ObjectMapper();
        T deepCopy = null;
        try {
            deepCopy = objectMapper
                    .readValue(objectMapper.writeValueAsString(object), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return deepCopy;
    }

*/