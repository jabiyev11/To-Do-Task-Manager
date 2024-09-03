package com.toDoTaskManager.Task.Manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {

    private String cityName;
    private String weatherDescription;
    private Double temperature;
    private Integer humidity;
    private Double windSpeed;
    private String icon;
    private String countryName;


}
