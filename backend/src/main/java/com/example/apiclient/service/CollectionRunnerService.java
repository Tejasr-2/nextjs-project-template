package com.example.apiclient.service;

import com.example.apiclient.model.Collection;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CollectionRunnerService {

    private final ApiRequestService apiRequestService;

    public CollectionRunnerService(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    /**
     * Runs the given collection with the provided data sets.
     * Each data set is a map of variable names to values.
     * Returns a list of results for each iteration.
     */
    public List<Map<String, Object>> runCollection(Collection collection, List<Map<String, Object>> dataSets) throws Exception {
        List<Map<String, Object>> results = new java.util.ArrayList<>();

        for (Map<String, Object> dataSet : dataSets) {
            for (Map<String, Object> request : collection.getRequests()) {
                // Substitute variables in request using dataSet and collection variables
                Map<String, Object> substitutedRequest = substituteVariables(request, dataSet, collection.getVariables());

                // Run pre-request script if present
                String preRequestScript = (String) substitutedRequest.get("preRequestScript");
                if (preRequestScript != null && !preRequestScript.isEmpty()) {
                    substitutedRequest = new PreRequestScriptService().runPreRequestScript(preRequestScript, substitutedRequest);
                }

                // Send request and get response
                String responseJson = new ApiRequestService(new PreRequestScriptService()).sendRequest(substitutedRequest);

                // TODO: Run tests on response if any

                // Collect result
                Map<String, Object> result = new java.util.HashMap<>();
                result.put("request", substitutedRequest);
                result.put("response", responseJson);
                results.add(result);
            }
        }

        return results;
    }

    private Map<String, Object> substituteVariables(Map<String, Object> request, Map<String, Object> dataSet, Map<String, Object> collectionVariables) {
        // Simple variable substitution implementation
        // Replace {{var}} in strings with values from dataSet or collectionVariables
        Map<String, Object> substituted = new java.util.HashMap<>();
        for (Map.Entry<String, Object> entry : request.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                String str = (String) value;
                str = substituteString(str, dataSet, collectionVariables);
                substituted.put(entry.getKey(), str);
            } else {
                substituted.put(entry.getKey(), value);
            }
        }
        return substituted;
    }

    private String substituteString(String str, Map<String, Object> dataSet, Map<String, Object> collectionVariables) {
        String result = str;
        for (Map.Entry<String, Object> entry : dataSet.entrySet()) {
            String key = entry.getKey();
            Object val = entry.getValue();
            if (val != null) {
                result = result.replace("{{" + key + "}}", val.toString());
            }
        }
        if (collectionVariables != null) {
            for (Map.Entry<String, Object> entry : collectionVariables.entrySet()) {
                String key = entry.getKey();
                Object val = entry.getValue();
                if (val != null) {
                    result = result.replace("{{" + key + "}}", val.toString());
                }
            }
        }
        return result;
    }
}
