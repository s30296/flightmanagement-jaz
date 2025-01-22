package com.example.service.mappers;

import com.example.model.models.Flight;
import com.example.service.dto.FlightRequest;
import com.example.service.dto.FlightResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightMapper INSTANCE = Mappers.getMapper(FlightMapper.class);

    @Mapping(target = "flightNumber", source = "flightNumber")
    @Mapping(target = "departureTime", source = "departureTime")
    @Mapping(target = "status", source = "status")
    Flight toFlight(FlightRequest flightRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "flightNumber", source = "flightNumber")
    @Mapping(target = "departureTime", source = "departureTime")
    @Mapping(target = "arrivalTime", source = "arrivalTime")
    @Mapping(target = "weather", source = "weather")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "departureAirport", source = "departureAirport")
    @Mapping(target = "arrivalAirport", source = "arrivalAirport")
    FlightResponse toFlightResponse(Flight flight);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "flightNumber", source = "flightNumber")
    @Mapping(target = "departureTime", source = "departureTime")
    @Mapping(target = "arrivalTime", source = "arrivalTime")
    @Mapping(target = "weather", source = "weather")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "departureAirport", source = "departureAirport")
    @Mapping(target = "arrivalAirport", source = "arrivalAirport")
    Flight toFlightFromResponse(FlightResponse flightResponse);
}
