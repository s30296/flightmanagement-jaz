package com.example.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeatherResponse {
    private Long id;
    private String cityName;
    private String countryName;
    private String description;
    private double temperature;
    private double humidity;
    private double pressure;
    private double windSpeed;
    private double windDeg;
    private String windDegTitle;
    private LocalDateTime timestamp;
}
