package com.example.demo.providers;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import com.example.demo.graphql.dataFetchers.GraphQLDataFetchers;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class GraphQLProvider {

    private GraphQL graphQL;

    @Autowired 
    GraphQLDataFetchers graphQLDataFetchers;

    @Bean
    public GraphQL graphQL() { 
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("graphql/schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type(TypeRuntimeWiring.newTypeWiring("Query")
                    .dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher())
                )
                .type(TypeRuntimeWiring.newTypeWiring("Book")
                    .dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher())
                    .dataFetcher("pageCount", graphQLDataFetchers.getPageCountDataFetcher())
                )
                .build();

        // .fieldVisibility(fieldVisibility)
        // https://www.graphql-java.com/documentation/v15/fieldvisibility/
    }
}






/*

{
    bookById(id: "book-1"){
        id
        name
        pageCount
        author{
            id
            firstName
            lastName
        }
    }

}



*/