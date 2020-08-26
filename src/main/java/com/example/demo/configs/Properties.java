package com.example.demo.configs;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
@Configuration
@Validated
@ConfigurationProperties(prefix = "custom")
@Data
public class Properties {
    @NotEmpty
    private String value;

    @Min(value = 10)
    @Max(value = 20)
    private Integer value2;
}