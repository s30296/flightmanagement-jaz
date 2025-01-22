package com.example.service.mappers;

import com.example.model.models.Airport;
import com.example.service.dto.AirportRequest;
import com.example.service.dto.AirportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportMapper INSTANCE = Mappers.getMapper(AirportMapper.class);

    @Mapping(source = "cityId", target = "city.id")
    Airport toAirport(AirportRequest airportRequest);

    @Mapping(source = "city.id", target = "cityId")
    AirportResponse toAirportResponse(Airport airport);
}