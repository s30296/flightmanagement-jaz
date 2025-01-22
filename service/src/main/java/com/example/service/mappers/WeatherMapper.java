package com.example.service.mappers;

import com.example.service.dto.WeatherRequest;
import com.example.service.dto.WeatherResponse;
import com.example.model.models.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeatherMapper {

    @Mapping(source = "cityName", target = "cityName")
    Weather toWeather(WeatherRequest weatherRequest);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "cityName", target = "cityName")
    @Mapping(source = "countryName", target = "countryName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "temperature", target = "temperature")
    @Mapping(source = "humidity", target = "humidity")
    @Mapping(source = "pressure", target = "pressure")
    @Mapping(source = "windSpeed", target = "windSpeed")
    @Mapping(source = "windDeg", target = "windDeg")
    @Mapping(source = "windDegTitle", target = "windDegTitle")
    @Mapping(source = "timestamp", target = "timestamp")
    WeatherResponse toWeatherResponse(Weather weather);
}
