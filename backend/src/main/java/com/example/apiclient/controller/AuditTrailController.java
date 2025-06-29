package com.example.apiclient.controller;

import com.example.apiclient.model.AuditTrail;
import com.example.apiclient.service.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audittrails")
public class AuditTrailController {

    private final AuditTrailService auditTrailService;

    @Autowired
    public AuditTrailController(AuditTrailService auditTrailService) {
        this.auditTrailService = auditTrailService;
    }

    @GetMapping
    public ResponseEntity<List<AuditTrail>> getAllAuditTrails() {
        try {
            List<AuditTrail> auditTrails = auditTrailService.getAllAuditTrails();
            return ResponseEntity.ok(auditTrails);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<AuditTrail> saveAuditTrail(@RequestBody AuditTrail auditTrail) {
        try {
            AuditTrail saved = auditTrailService.saveAuditTrail(auditTrail);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
