package com.toDoTaskManager.Task.Manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryService {

    private final RestTemplate restTemplate;

    public CountryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getCountryName(String countryCode) {

        String url = "https://restcountries.com/v3.1/alpha/" + countryCode;

        try {
            String response = restTemplate.getForObject(url, String.class);

            JsonNode root = new ObjectMapper().readTree(response);
            return root.path(0).path("name").path("common").asText();

        } catch (JsonProcessingException e) {
            System.out.println("Problem during country code data fetching");
            return "Unknown Country";
        }
    }
}
