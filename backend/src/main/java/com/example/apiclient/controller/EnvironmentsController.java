package com.example.apiclient.controller;

import com.example.apiclient.model.Environment;
import com.example.apiclient.service.EnvironmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/environments")
public class EnvironmentsController {

    private final EnvironmentsService environmentsService;

    @Autowired
    public EnvironmentsController(EnvironmentsService environmentsService) {
        this.environmentsService = environmentsService;
    }

    @GetMapping
    public ResponseEntity<List<Environment>> getAllEnvironments() {
        try {
            List<Environment> environments = environmentsService.getAllEnvironments();
            return ResponseEntity.ok(environments);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Environment> getEnvironmentById(@PathVariable String id) {
        try {
            Environment environment = environmentsService.getEnvironmentById(id);
            if (environment == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(environment);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Environment> saveEnvironment(@RequestBody Environment environment) {
        try {
            Environment saved = environmentsService.saveEnvironment(environment);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvironment(@PathVariable String id) {
        try {
            boolean deleted = environmentsService.deleteEnvironment(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
