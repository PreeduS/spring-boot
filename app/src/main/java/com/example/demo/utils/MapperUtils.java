package com.example.demo.utils;

import java.util.function.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapperUtils {
    @Autowired
    private ObjectMapper objectMapper;

    /*public MapperUtils(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }*/
    public void configure(Consumer<ObjectMapper> objectMapper){
        objectMapper.accept(this.objectMapper);
    }

    public JsonNode parse(String src) throws JsonMappingException, JsonProcessingException {
        return objectMapper.readTree(src);
    }
    public <T> T fromJson(JsonNode node, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.treeToValue(node, valueType);
    }
    public JsonNode toJson(Object object) {
        return objectMapper.valueToTree(object);
    }
    public String stringify(JsonNode node) throws JsonProcessingException {
        ObjectWriter writer = objectMapper.writer();
        // writer.with(SerializationFeature.INDENT_OUTPUT);
        return writer.writeValueAsString(node);
    }
    public <S, T> T mapToObject(S source, Class<T> target)  {
        return objectMapper.convertValue(source, target);
    }
}