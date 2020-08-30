package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class TestEntityDto {
    private String p;
    private Integer p2;
    private List<String> p3;
    private MapperDto mapper;

}