package com.example.apiclient.controller;

import com.example.apiclient.service.ApiRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
public class ApiRequestController {

    private final ApiRequestService apiRequestService;

    @Autowired
    public ApiRequestController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendRequest(@RequestBody String requestPayload) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            java.util.Map<String, Object> requestMap = mapper.readValue(requestPayload, java.util.Map.class);

            String responseJson = apiRequestService.sendRequest(requestMap);

            return ResponseEntity.ok(responseJson);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error executing request: " + e.getMessage());
        }
    }
}
