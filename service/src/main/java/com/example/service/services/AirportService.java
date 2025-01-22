package com.example.service.services;

import com.example.model.models.Airport;
import com.example.model.models.City;
import com.example.model.repositories.AirportRepository;
import com.example.model.repositories.CityRepository;
import com.example.service.dto.AirportRequest;
import com.example.service.dto.AirportResponse;
import com.example.service.mappers.AirportMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;
    private final AirportMapper airportMapper;

    public AirportService(AirportRepository airportRepository, CityRepository cityRepository, AirportMapper airportMapper) {
        this.airportRepository = airportRepository;
        this.cityRepository = cityRepository;
        this.airportMapper = airportMapper;
    }

    // Zwraca liste wszystkich lotnisk.
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(airport -> airportMapper.toAirportResponse(airport))
                .collect(Collectors.toList());
    }

    // Zwraca liste wszystkich lotnisk zapisanych w cache.
    @Cacheable(value = "getAllCachedAirports")
    public List<AirportResponse> getAllCachedAirports() {
        log.info("getAllAirports zostanie teraz zapisane w cache, nastepne wywolanie nie wyswietli tej wiadomosci i dane zostana wyswietlone z cache");
        return airportRepository.findAll().stream()
                .map(airport -> airportMapper.toAirportResponse(airport))
                .collect(Collectors.toList());
    }

    // Zwraca lotnisko po id.
    public AirportResponse getAirportById(Long id) {
        return airportRepository.findById(id)
                .map(airportMapper::toAirportResponse)
                .orElseThrow(() -> new RuntimeException("Airport not found"));
    }

    // Tworzy lotnisko.
    public AirportResponse createAirport(AirportRequest request) {
        Airport airport = new Airport();
        airport.setName(request.getName());

        City city = cityRepository.findById(request.getCityId())
                .orElseThrow(() -> new RuntimeException("Miasto nie znalezione"));
        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);

        return airportMapper.toAirportResponse(savedAirport);
    }

    // Test czy AOP dziala.
    public AirportResponse bug(AirportRequest request) {
        City city = cityRepository.findById(request.getCityId() + 50)
                .orElseThrow(() -> new IllegalArgumentException("Miasto nie znalezione dla ID: " + request.getCityId()));

        Airport airport = new Airport();
        airport.setName(request.getName());
        airport.setCity(city);

        Airport savedAirport = airportRepository.save(airport);

        return airportMapper.toAirportResponse(savedAirport);
    }
}
