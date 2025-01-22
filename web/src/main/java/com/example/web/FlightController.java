package com.example.web;

import com.example.model.models.Flight;
import com.example.model.models.FlightStatus;
import com.example.service.dto.FlightRequest;
import com.example.service.dto.FlightResponse;
import com.example.service.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // Metoda GET wyswietlajaca wszystkie loty.
    @GetMapping("getAllFlights")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    // Metoda GET wyswietlajaca konkretny lot na podstawie id.
    @GetMapping("/getFlightById/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    // Metoda GET wyswietlajaca liste lotow na podstawie zapytanego statusu.
    @GetMapping("/getFlightsByStatus/{status}")
    public List<Flight> getFlightsByStatus(@PathVariable FlightStatus status) {
        return flightService.getFlightsByStatus(status);
    }

    /* Metoda POST tworzaca lot.
    Przykladowy JSON:
    {
        "flightNumber": "ABCXYZ1234",
        "flightLength": 2,
        "departureTime": "2025-01-05T10:30:00",
        "departureAirportId": 1,
        "arrivalAirportId": 2
    }
    */
    @PostMapping("/create")
    public FlightResponse createFlight(@RequestBody FlightRequest flightRequest) {
        return flightService.createFlight(flightRequest);
    }

    // Metoda PUT aktualizujaca lot.
    @PutMapping("/update/{id}")
    public FlightResponse updateFlight(@PathVariable Long id, @RequestBody FlightRequest flightRequest) {
        return flightService.updateFlight(id, flightRequest);
    }

    // Metoda DELETE usuwajaca lot.
    @DeleteMapping("/delete/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
    }
}
