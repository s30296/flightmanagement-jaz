package com.example.service.mappers;

import com.example.service.dto.WeatherApiRequest;
import com.example.service.dto.WeatherApiResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WeatherApiMapper {

    @Mapping(target = "main.temp", ignore = true)
    @Mapping(target = "main.humidity", ignore = true)
    @Mapping(target = "main.pressure", ignore = true)
    @Mapping(target = "wind.speed", ignore = true)
    @Mapping(target = "wind.deg", ignore = true)
    @Mapping(target = "wind.degTitle", ignore = true)
    @Mapping(target = "weather", ignore = true)
    WeatherApiResponse mapRequestToResponse(WeatherApiRequest request);
}
