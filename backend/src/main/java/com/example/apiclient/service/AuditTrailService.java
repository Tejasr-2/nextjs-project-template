package com.example.apiclient.service;

import com.example.apiclient.model.AuditTrail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuditTrailService {

    private static final String AUDIT_DIR = "backend/data/audittrails";

    private final ObjectMapper mapper;

    public AuditTrailService() {
        this.mapper = new ObjectMapper();
        File dir = new File(AUDIT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public List<AuditTrail> getAllAuditTrails() throws Exception {
        List<AuditTrail> auditTrails = new ArrayList<>();
        File dir = new File(AUDIT_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                AuditTrail auditTrail = mapper.readValue(file, AuditTrail.class);
                auditTrails.add(auditTrail);
            }
        }
        return auditTrails;
    }

    public AuditTrail saveAuditTrail(AuditTrail auditTrail) throws Exception {
        if (auditTrail.getId() == null || auditTrail.getId().isEmpty()) {
            auditTrail.setId(UUID.randomUUID().toString());
        }
        auditTrail.setTimestamp(LocalDateTime.now());
        File file = new File(AUDIT_DIR, auditTrail.getId() + ".json");
        mapper.writeValue(file, auditTrail);
        return auditTrail;
    }
}
