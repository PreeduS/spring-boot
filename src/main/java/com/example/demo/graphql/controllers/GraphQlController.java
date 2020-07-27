package com.example.demo.graphql.controllers;

import java.util.Map;

import com.example.demo.graphql.Dtos.Data;
import com.example.demo.providers.GraphQLProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionInput;
import graphql.ExecutionResult;

@RestController
public class GraphQlController {
    @Autowired
    GraphQLProvider graphQLProvider;

    @PostMapping("/gql/test")
    public ResponseEntity<ExecutionResult> test(@RequestBody String query) {
        ExecutionResult executionResult = graphQLProvider.graphQL().execute(query);

        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }

    @PostMapping("/gql/test2")
    public ResponseEntity<ExecutionResult> test2(@RequestBody Data data) {
        ExecutionResult executionResult = graphQLProvider.graphQL().execute(data.getQuery());

        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }
    @PostMapping("/gql/test3")
    public ResponseEntity<ExecutionResult> test3(@RequestBody Data data) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput().query(data.getQuery())
            .build();
        ExecutionResult executionResult = graphQLProvider.graphQL().execute(executionInput);

        return new ResponseEntity<>(executionResult, HttpStatus.OK);
    }

    
}