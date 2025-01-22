package com.example.service.services;

import com.example.model.models.Weather;
import com.example.model.repositories.WeatherRepository;
import com.example.service.dto.WeatherApiResponse;
import com.example.service.dto.WeatherRequest;
import com.example.service.dto.WeatherResponse;
import com.example.service.mappers.WeatherApiMapper;
import com.example.service.mappers.WeatherMapper;
import com.example.service.tools.StringTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.url}")
    private String apiUrl;

    private final WeatherRepository weatherRepository;
    private final WeatherApiMapper weatherApiMapper;
    private final WeatherMapper weatherMapper;

    public WeatherService(WeatherRepository weatherRepository, WeatherApiMapper weatherApiMapper, WeatherMapper weatherMapper) {
        this.weatherRepository = weatherRepository;
        this.weatherApiMapper = weatherApiMapper;
        this.weatherMapper = weatherMapper;
    }

    // Pobranie listy wszystkich rekordow pogodowych w lokalnej bazie danych.
    public List<WeatherResponse> getAllWeather() {
        List<Weather> weathers = weatherRepository.findAll();
        return weathers.stream()
                .map(weather -> weatherMapper.toWeatherResponse(weather))
                .collect(Collectors.toList());
    }

    // Pobranie najnowszych danych pogodowych z lokalnej bazy danych dla konkretnego miasta.
    public WeatherResponse getLatestWeatherByCity(String cityName) {
        Weather weather = weatherRepository.findTopByCityNameOrderByTimestampDesc(cityName)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono danych pogodowych dla miasta: " + cityName));

        return weatherMapper.toWeatherResponse(weather);
    }

    // Pobranie danych pogodowych z lokalnej bazy danych za pomoca id rekordu.
    public WeatherResponse getWeatherById(WeatherRequest weatherRequest) {
        Weather weather = weatherRepository.findById(weatherRequest.getId()).orElse(null);
        return weatherMapper.toWeatherResponse(weather);
    }

    // Pobranie najnowszych danych pogodowych dla miasta bezposrednio z API.
    public Weather getWeatherByCityFromApi(String cityName) {
        String url = String.format("%s/weather?q=%s&appid=%s&units=metric", apiUrl, cityName, apiKey);

        RestTemplate restTemplate = new RestTemplate();

        try {
            WeatherApiResponse response = restTemplate.getForObject(url, WeatherApiResponse.class);

            if (response == null) {
                throw new RuntimeException("Brak danych z API");
            }

            Weather weather = new Weather();
            weather.setCityName(cityName);
            weather.setCountryName(response.getSys().getCountry());
            weather.setDescription(StringTools.capitalizeWords(response.getWeather().get(0).getDescription()));
            weather.setTemperature(response.getMain().getTemp());
            weather.setHumidity(response.getMain().getHumidity());
            weather.setPressure(response.getMain().getPressure());
            weather.setWindSpeed(response.getWind().getSpeed());
            weather.setWindDeg(response.getWind().getDeg());
            weather.setTimestamp(LocalDateTime.now());

            if (response.getWind().getDeg() >= 0 && response.getWind().getDeg() < 22.5) {
                weather.setWindDegTitle("North (N)");
            } else if (response.getWind().getDeg() >= 22.5 && response.getWind().getDeg() < 67.5) {
                weather.setWindDegTitle("North East (NE)");
            } else if (response.getWind().getDeg() >= 67.5 && response.getWind().getDeg() < 112.5) {
                weather.setWindDegTitle("East (E)");
            } else if (response.getWind().getDeg() >= 112.5 && response.getWind().getDeg() < 157.5) {
                weather.setWindDegTitle("South East (SE)");
            } else if (response.getWind().getDeg() >= 157.5 && response.getWind().getDeg() < 202.5) {
                weather.setWindDegTitle("South (S)");
            } else if (response.getWind().getDeg() >= 202.5 && response.getWind().getDeg() < 247.5) {
                weather.setWindDegTitle("South West (SW)");
            } else if (response.getWind().getDeg() >= 247.5 && response.getWind().getDeg() < 292.5) {
                weather.setWindDegTitle("West (W)");
            } else if (response.getWind().getDeg() >= 292.5 && response.getWind().getDeg() < 337.5) {
                weather.setWindDegTitle("North West (NW)");
            } else if (response.getWind().getDeg() >= 337.5 && response.getWind().getDeg() < 360) {
                weather.setWindDegTitle("North (N)");
            } else {
                weather.setWindDegTitle("Unknown direction");
            }

            return weatherRepository.save(weather);
        } catch (Exception e) {
            throw new RuntimeException("Problem z polaczeniem z API: " + e.getMessage());
        }
    }

    // Usuniecie wszystkich rekordow pogodowych z lokalnej bazy danych.
    public void deleteAllWeather() {
        weatherRepository.deleteAll();
    }
}
