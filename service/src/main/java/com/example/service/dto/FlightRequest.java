package com.example.service.dto;

import com.example.model.models.FlightStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FlightRequest {
    private String flightNumber;
    private Long flightLength;
    private LocalDateTime departureTime;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private FlightStatus status;
}
