package com.example.apiclient.model;

import java.util.List;
import java.util.Map;

public class Collection {
    private String id;
    private String name;
    private String description;
    private List<Map<String, Object>> requests; // List of API requests in the collection
    private Map<String, Object> variables; // Collection variables
    private String version;

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Map<String, Object>> getRequests() {
        return requests;
    }

    public void setRequests(List<Map<String, Object>> requests) {
        this.requests = requests;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
