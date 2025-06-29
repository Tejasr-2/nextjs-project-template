package com.example.apiclient.controller;

import com.example.apiclient.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> search(@RequestParam("q") String query) {
        List<Map<String, Object>> results = searchService.search(query);
        return ResponseEntity.ok(results);
    }
}
