package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AppwriteService {

    private static final String APPWRITE_ENDPOINT = "https://cloud.appwrite.io/v1";
    private static final String API_KEY = "your-api-key";  // Replace with your Appwrite API key

    private final RestTemplate restTemplate;

    public AppwriteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createCollection(String dbId, String collectionName) {
        // Make API call to Appwrite to create collection
        String url = APPWRITE_ENDPOINT + "/databases/" + dbId + "/collections";
        // Add your headers and request body as needed for Appwrite
    }
}
