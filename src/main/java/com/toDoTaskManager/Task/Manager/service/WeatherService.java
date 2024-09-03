package com.toDoTaskManager.Task.Manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toDoTaskManager.Task.Manager.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final CountryService countryService;

    public WeatherService(RestTemplate restTemplate, CountryService countryService) {
        this.restTemplate = restTemplate;
        this.countryService = countryService;
    }

    public WeatherResponse getWeather(String city) {
        WeatherResponse weatherResponse = null;
        try {
            String url = UriComponentsBuilder.fromHttpUrl("http://api.openweathermap.org/data/2.5/weather")
                    .queryParam("q", city)
                    .queryParam("appid", apiKey)
                    .queryParam("units", "metric")
                    .toUriString();

            String jsonResponse = restTemplate.getForObject(url, String.class);

            JsonNode root = new ObjectMapper().readTree(jsonResponse);

            weatherResponse = new WeatherResponse();
            weatherResponse.setCityName(root.path("name").asText());
            weatherResponse.setWeatherDescription(root.path("weather").get(0).path("description").asText());
            weatherResponse.setTemperature(root.path("main").path("temp").asDouble());
            weatherResponse.setHumidity(root.path("main").path("humidity").asInt());
            weatherResponse.setWindSpeed(root.path("wind").path("speed").asDouble());
            weatherResponse.setIcon(root.path("weather").get(0).path("icon").asText());

            String countryName = root.path("sys").path("country").asText();
            weatherResponse.setCountryName(countryService.getCountryName(countryName));

        } catch (JsonProcessingException e) {
            System.out.println("Problem during data fetching");
        }

        return weatherResponse;
    }
}
