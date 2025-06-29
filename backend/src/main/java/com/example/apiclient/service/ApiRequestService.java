package com.example.apiclient.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class ApiRequestService {

    private final HttpClient client;
    private final ObjectMapper mapper;
    private final PreRequestScriptService preRequestScriptService;

    @Autowired
    public ApiRequestService(PreRequestScriptService preRequestScriptService) {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.preRequestScriptService = preRequestScriptService;
    }

    public String sendRequest(Map<String, Object> requestMap) throws Exception {
        // Run pre-request script if present
        String preRequestScript = (String) requestMap.get("preRequestScript");
        if (preRequestScript != null && !preRequestScript.isEmpty()) {
            requestMap = preRequestScriptService.runPreRequestScript(preRequestScript, requestMap);
        }

        String method = (String) requestMap.getOrDefault("method", "GET");
        String url = (String) requestMap.get("url");
        Map<String, String> headers = (Map<String, String>) requestMap.getOrDefault("headers", Map.of());
        String body = (String) requestMap.getOrDefault("body", null);
        Map<String, Object> auth = (Map<String, Object>) requestMap.getOrDefault("auth", null);

        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL is required");
        }

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url));

        // Apply authentication headers or parameters
        if (auth != null) {
            applyAuthentication(requestBuilder, auth);
        }

        switch (method.toUpperCase()) {
            case "POST":
                if (body != null) {
                    requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body));
                } else {
                    requestBuilder.POST(HttpRequest.BodyPublishers.noBody());
                }
                break;
            case "PUT":
                if (body != null) {
                    requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(body));
                } else {
                    requestBuilder.PUT(HttpRequest.BodyPublishers.noBody());
                }
                break;
            case "DELETE":
                requestBuilder.DELETE();
                break;
            case "PATCH":
                if (body != null) {
                    requestBuilder.method("PATCH", HttpRequest.BodyPublishers.ofString(body));
                } else {
                    requestBuilder.method("PATCH", HttpRequest.BodyPublishers.noBody());
                }
                break;
            case "HEAD":
                requestBuilder.method("HEAD", HttpRequest.BodyPublishers.noBody());
                break;
            case "OPTIONS":
                requestBuilder.method("OPTIONS", HttpRequest.BodyPublishers.noBody());
                break;
            case "GET":
            default:
                requestBuilder.GET();
                break;
        }

        // Add headers
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.header(entry.getKey(), entry.getValue());
        }

        HttpRequest request = requestBuilder.build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Map<String, Object> responseMap = Map.of(
                "statusCode", response.statusCode(),
                "headers", response.headers().map(),
                "body", response.body()
        );

        return mapper.writeValueAsString(responseMap);
    }

    private void applyAuthentication(HttpRequest.Builder requestBuilder, Map<String, Object> auth) {
        String type = (String) auth.get("type");
        if (type == null) return;

        switch (type.toLowerCase()) {
            case "apikey":
                // Example: add API key as header or query param
                String in = (String) auth.get("in"); // "header" or "query"
                String key = (String) auth.get("key");
                String value = (String) auth.get("value");
                if ("header".equalsIgnoreCase(in)) {
                    requestBuilder.header(key, value);
                } else if ("query".equalsIgnoreCase(in)) {
                    // Append query param to URL
                    URI uri = requestBuilder.build().uri();
                    String newQuery = uri.getQuery() == null ? "" : uri.getQuery() + "&";
                    newQuery += key + "=" + value;
                    try {
                        URI newUri = new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), newQuery, uri.getFragment());
                        requestBuilder.uri(newUri);
                    } catch (Exception e) {
                        // ignore
                    }
                }
                break;
            case "bearertoken":
                String token = (String) auth.get("token");
                if (token != null) {
                    requestBuilder.header("Authorization", "Bearer " + token);
                }
                break;
            case "basicauth":
                String username = (String) auth.get("username");
                String password = (String) auth.get("password");
                if (username != null && password != null) {
                    String encoded = java.util.Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                    requestBuilder.header("Authorization", "Basic " + encoded);
                }
                break;
            // TODO: Implement OAuth 1.0 & 2.0, Hawk, Digest, NTLM as needed
            default:
                break;
        }
    }
}
