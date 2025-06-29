package com.example.apiclient.service;

import com.example.apiclient.model.Monitor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MonitorsService {

    private static final String MONITORS_DIR = "backend/data/monitors";

    private final ObjectMapper mapper;

    public MonitorsService() {
        this.mapper = new ObjectMapper();
        File dir = new File(MONITORS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public List<Monitor> getAllMonitors() throws Exception {
        List<Monitor> monitors = new ArrayList<>();
        File dir = new File(MONITORS_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                Monitor monitor = mapper.readValue(file, Monitor.class);
                monitors.add(monitor);
            }
        }
        return monitors;
    }

    public Monitor getMonitorById(String id) throws Exception {
        File file = new File(MONITORS_DIR, id + ".json");
        if (!file.exists()) {
            return null;
        }
        return mapper.readValue(file, Monitor.class);
    }

    public Monitor saveMonitor(Monitor monitor) throws Exception {
        if (monitor.getId() == null || monitor.getId().isEmpty()) {
            monitor.setId(UUID.randomUUID().toString());
        }
        File file = new File(MONITORS_DIR, monitor.getId() + ".json");
        mapper.writeValue(file, monitor);
        return monitor;
    }

    public boolean deleteMonitor(String id) throws Exception {
        File file = new File(MONITORS_DIR, id + ".json");
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    // Placeholder for scheduling and running monitors
    public void runMonitor(Monitor monitor) {
        // TODO: Implement scheduled runs and alerting
        monitor.setLastRun(LocalDateTime.now());
        monitor.setLastStatus("success");
        try {
            saveMonitor(monitor);
        } catch (Exception e) {
            // log error
        }
    }
}
