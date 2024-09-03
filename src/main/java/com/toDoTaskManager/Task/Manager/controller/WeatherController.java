package com.toDoTaskManager.Task.Manager.controller;

import com.toDoTaskManager.Task.Manager.dto.WeatherResponse;
import com.toDoTaskManager.Task.Manager.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public String getWeather(@RequestParam(required = false, defaultValue = "London") String city, Model model){

        if(city == null || city.trim().isEmpty()){
            model.addAttribute("error", "City name cannot be empty");
            return "weather";
        }

        WeatherResponse weatherResponse = weatherService.getWeather(city);
        model.addAttribute("weather", weatherResponse);
        return "weather";
    }
}
