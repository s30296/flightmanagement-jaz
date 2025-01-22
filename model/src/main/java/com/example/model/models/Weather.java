package com.example.model.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
