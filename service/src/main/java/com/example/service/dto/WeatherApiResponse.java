package com.example.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherApiResponse {

    private Main main;
    private Wind wind;
    private Sys sys;
    private List<WeatherDescription> weather;

    @Getter
    @Setter
    public static class Main {
        private double temp;
        private double humidity;
        private double pressure;
    }

    @Getter
    @Setter
    public static class Wind {
        private double speed;
        private double deg;
        private String degTitle;
    }

    @Getter
    @Setter
    public static class Sys {
        private String country;
    }

    @Getter
    @Setter
    public static class WeatherDescription {
        private String description;
    }
}
