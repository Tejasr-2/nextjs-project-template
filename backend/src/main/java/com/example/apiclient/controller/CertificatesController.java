package com.example.apiclient.controller;

import com.example.apiclient.model.Certificate;
import com.example.apiclient.service.CertificatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificatesController {

    private final CertificatesService certificatesService;

    @Autowired
    public CertificatesController(CertificatesService certificatesService) {
        this.certificatesService = certificatesService;
    }

    @GetMapping
    public ResponseEntity<List<Certificate>> getAllCertificates() {
        try {
            List<Certificate> certificates = certificatesService.getAllCertificates();
            return ResponseEntity.ok(certificates);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certificate> getCertificateById(@PathVariable String id) {
        try {
            Certificate certificate = certificatesService.getCertificateById(id);
            if (certificate == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(certificate);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Certificate> saveCertificate(@RequestBody Certificate certificate) {
        try {
            Certificate saved = certificatesService.saveCertificate(certificate);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable String id) {
        try {
            boolean deleted = certificatesService.deleteCertificate(id);
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
