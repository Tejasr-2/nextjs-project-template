package com.example.apiclient.service;

import com.example.apiclient.model.Certificate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CertificatesService {

    private static final String CERTIFICATES_DIR = "backend/data/certificates";

    private final ObjectMapper mapper;

    public CertificatesService() {
        this.mapper = new ObjectMapper();
        File dir = new File(CERTIFICATES_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public List<Certificate> getAllCertificates() throws Exception {
        List<Certificate> certificates = new ArrayList<>();
        File dir = new File(CERTIFICATES_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                Certificate certificate = mapper.readValue(file, Certificate.class);
                certificates.add(certificate);
            }
        }
        return certificates;
    }

    public Certificate getCertificateById(String id) throws Exception {
        File file = new File(CERTIFICATES_DIR, id + ".json");
        if (!file.exists()) {
            return null;
        }
        return mapper.readValue(file, Certificate.class);
    }

    public Certificate saveCertificate(Certificate certificate) throws Exception {
        if (certificate.getId() == null || certificate.getId().isEmpty()) {
            certificate.setId(UUID.randomUUID().toString());
        }
        File file = new File(CERTIFICATES_DIR, certificate.getId() + ".json");
        mapper.writeValue(file, certificate);
        return certificate;
    }

    public boolean deleteCertificate(String id) throws Exception {
        File file = new File(CERTIFICATES_DIR, id + ".json");
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
