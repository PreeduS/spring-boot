package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class TestEntityTargetDto {
    private String p;
    private Integer p2;
    private List<String> p4;
    private MapperDto mapper;
}