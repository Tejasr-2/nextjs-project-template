package com.example.apiclient.controller;

import com.example.apiclient.model.Monitor;
import com.example.apiclient.service.MonitorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitors")
public class MonitorsController {

    private final MonitorsService monitorsService;

    @Autowired
    public MonitorsController(MonitorsService monitorsService) {
        this.monitorsService = monitorsService;
    }

    @GetMapping
    public ResponseEntity<List<Monitor>> getAllMonitors() {
        try {
            List<Monitor> monitors = monitorsService.getAllMonitors();
            return ResponseEntity.ok(monitors);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monitor> getMonitorById(@PathVariable String id) {
        try {
            Monitor monitor = monitorsService.getMonitorById(id);
            if (monitor == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(monitor);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Monitor> saveMonitor(@RequestBody Monitor monitor) {
        try {
            Monitor saved = monitorsService.saveMonitor(monitor);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMonitor(@PathVariable String id) {
        try {
            boolean deleted = monitorsService.deleteMonitor(id);
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
