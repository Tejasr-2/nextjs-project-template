package com.example.apiclient.service;

import com.example.apiclient.model.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EnvironmentsService {

    private static final String ENVIRONMENTS_DIR = "backend/data/environments";

    private final ObjectMapper mapper;

    public EnvironmentsService() {
        this.mapper = new ObjectMapper();
        File dir = new File(ENVIRONMENTS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public List<Environment> getAllEnvironments() throws Exception {
        List<Environment> environments = new ArrayList<>();
        File dir = new File(ENVIRONMENTS_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                Environment environment = mapper.readValue(file, Environment.class);
                environments.add(environment);
            }
        }
        return environments;
    }

    public Environment getEnvironmentById(String id) throws Exception {
        File file = new File(ENVIRONMENTS_DIR, id + ".json");
        if (!file.exists()) {
            return null;
        }
        return mapper.readValue(file, Environment.class);
    }

    public Environment saveEnvironment(Environment environment) throws Exception {
        if (environment.getId() == null || environment.getId().isEmpty()) {
            environment.setId(UUID.randomUUID().toString());
        }
        File file = new File(ENVIRONMENTS_DIR, environment.getId() + ".json");
        mapper.writeValue(file, environment);
        return environment;
    }

    public boolean deleteEnvironment(String id) throws Exception {
        File file = new File(ENVIRONMENTS_DIR, id + ".json");
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
