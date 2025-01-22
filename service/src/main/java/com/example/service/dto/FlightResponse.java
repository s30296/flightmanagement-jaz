package com.example.service.dto;

import com.example.model.models.Airport;
import com.example.model.models.FlightStatus;
import com.example.model.models.Weather;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FlightResponse {
    private Long id;
    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Weather weather;
    private FlightStatus status;
}
