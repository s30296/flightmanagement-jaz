package com.example.web;

import com.example.service.dto.AirportRequest;
import com.example.service.dto.AirportResponse;
import com.example.service.services.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    /* Metoda POST tworzaca lotnisko.
    Przykalad JSON:
    {
        "name": "WAW",
        "cityId": 1
    }
    */
    @PostMapping("/create")
    public AirportResponse createAirport(@RequestBody AirportRequest airport) {
        return airportService.createAirport(airport);
    }

    // Metoda POST testujaca czy AOP dziala, wyrzuca blad.
    @PostMapping("/bug")
    public AirportResponse bug(@RequestBody AirportRequest airport) {
        return airportService.bug(airport);
    }

    // Metoda GET zwracajaca liste wszystkich lotnisk.
    @GetMapping("/getAllAirports")
    public List<AirportResponse> getAllAirports() {
        return airportService.getAllAirports();
    }

    // Metoda GET zwracajaca liste wszystkich lotnisk w cache.
    @GetMapping("/getAllCachedAirports")
    public List<AirportResponse> getAllCachedAirports() {
        return airportService.getAllCachedAirports();
    }

    // Metoda GET zwracajaca lotnisko za pomoca id.
    @GetMapping("/getAirportById/{id}")
    public AirportResponse getAirportById(@PathVariable Long id) {
        return airportService.getAirportById(id);
    }
}
