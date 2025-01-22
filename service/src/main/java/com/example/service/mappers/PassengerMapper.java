package com.example.service.mappers;

import com.example.model.models.Passenger;
import com.example.service.dto.PassengerRequest;
import com.example.service.dto.PassengerResponse;
import com.example.model.models.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    PassengerMapper INSTANCE = Mappers.getMapper(PassengerMapper.class);

    @Mapping(target = "flights", source = "flights")
    Passenger toPassenger(PassengerRequest passengerRequest);

    @Mapping(source = "flights", target = "flights")
    PassengerResponse toPassengerResponse(Passenger passenger);

    // Przez to ze MapStruct nie poradzil sobie z mapowaniem list musialem wykonac samemu mapFlightIdsToEntities oraz mapFlightIdsToFlights
    default List<Flight> mapFlightIdsToEntities(List<Long> flights) {
        if (flights == null) {
            return null;
        }
        return flights.stream()
                .map(id -> new Flight(id))
                .collect(Collectors.toList());
    }

    default List<Long> mapFlightIdsToFlights(List<Flight> flights) {
        if (flights == null) {
            return null;
        }
        return flights.stream()
                .map(flight -> flight.getId())
                .collect(Collectors.toList());
    }
}
