package com.example.demo.graphql.Dtos;

import java.util.Map;

public class Data {
    private String query;
    private String operationName;
    private Map<String, String> variables;

    public String getQuery() {
        return query;
    }

    public Map<String, String> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}