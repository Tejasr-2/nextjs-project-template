package com.example.apiclient.controller;

import com.example.apiclient.service.GraphQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/graphql")
public class GraphQLController {

    private final GraphQLService graphQLService;

    @Autowired
    public GraphQLController(GraphQLService graphQLService) {
        this.graphQLService = graphQLService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> executeQuery(@RequestBody Map<String, Object> request) {
        String query = (String) request.get("query");
        Map<String, Object> variables = (Map<String, Object>) request.get("variables");

        Map<String, Object> result = graphQLService.executeQuery(query, variables);

        return ResponseEntity.ok(result);
    }
}
