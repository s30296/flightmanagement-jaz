package com.example.service.services;

import com.example.model.models.*;
import com.example.model.repositories.AirportRepository;
import com.example.model.repositories.FlightRepository;
import com.example.model.repositories.PassengerRepository;
import com.example.model.repositories.WeatherRepository;
import com.example.service.dto.FlightRequest;
import com.example.service.dto.FlightResponse;
import com.example.service.mappers.FlightMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final AirportRepository airportRepository;
    private final PassengerRepository passengerRepository;
    private final WeatherRepository weatherRepository;
    private final WeatherService weatherService;

    @Autowired
    public FlightService(FlightRepository flightRepository, FlightMapper flightMapper, AirportRepository airportRepository, WeatherService weatherService, PassengerRepository passengerRepository, WeatherRepository weatherRepository) {
        this.flightRepository = flightRepository;
        this.flightMapper = flightMapper;
        this.airportRepository = airportRepository;
        this.weatherService = weatherService;
        this.passengerRepository = passengerRepository;
        this.weatherRepository = weatherRepository;
    }

    // Zwraca wszystkie loty z bazy danych.
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Zwaraca lot za pomoca id.
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElse(null);
    }

    // Zwraca lot za pomoca statusu.
    public List<Flight> getFlightsByStatus(FlightStatus status) {
        return flightRepository.findByStatus(status);
    }

    // Tworzy lot na podstawie Request Body.
    public FlightResponse createFlight(FlightRequest flightRequest) {
        Flight flight = processFlightData(null, flightRequest);
        flight = flightRepository.save(flight);
        return flightMapper.toFlightResponse(flight);
    }

    // Aktualizuje lot na podstawie Request Body oraz id.
    public FlightResponse updateFlight(Long id, FlightRequest flightRequest) {
        Flight existingFlight = flightRepository.findById(id).orElse(null);
        if (existingFlight == null) {
            return null;
        }

        Flight updatedFlight = processFlightData(existingFlight, flightRequest);
        updatedFlight = flightRepository.save(updatedFlight);
        return flightMapper.toFlightResponse(updatedFlight);
    }

    /* Metoda pomocnicza dla createFlight ora updateFlight, ustawia dane o locie z Request Body, dostosowuje czas na podstawie
    podanej wartosci w Request Body, pobiera najbardziej aktualna pogode z OpenWeatherMapApi, a jesli nie jest ona dostepna to pobiera z DB, albo ustawia null,
    w zaleznosci od tego czy lot jest anulowany, badz czy wiatr jest zbyt silny ustawia odpowiednio ON_TIME, DELAYED, CANCELLED i dostosowuje opoznienie lotu.*/
    private Flight processFlightData(Flight existingFlight, FlightRequest flightRequest) {
        Airport departureAirport = airportRepository.findById(flightRequest.getDepartureAirportId()).orElse(null);
        Airport arrivalAirport = airportRepository.findById(flightRequest.getArrivalAirportId()).orElse(null);

        if (departureAirport == null || arrivalAirport == null) {
            throw new IllegalArgumentException("Niepoprawne ID lotniska");
        }

        Flight flight;
        if (existingFlight != null) {
            flight = existingFlight;
        } else {
            flight = new Flight();
        }
        flight.setFlightNumber(flightRequest.getFlightNumber());
        flight.setDepartureTime(flightRequest.getDepartureTime());
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);

        Weather weather = null;
        try {
            weather = weatherService.getWeatherByCityFromApi(departureAirport.getCity().getName());
            if (weather == null) {
                throw new Exception("Pogoda z API jest pusta.");
            }
        } catch (Exception e) {
            System.out.println("Blad podczas pobierania pogody z API: " + e.getMessage() + " Pogoda zostanie wyznaczona na podstawie najnowszych danych z DB.");
            weather = weatherRepository.findTopByCityNameOrderByTimestampDesc(departureAirport.getCity().getName())
                    .orElse(null);
            if (weather == null) {
                System.out.println("Nie znaleziono pogody w bazie danych dla miasta: " + departureAirport.getCity().getName());
            }
        }
        flight.setWeather(weather);


        flight.setArrivalTime(flightRequest.getDepartureTime().plusHours(flightRequest.getFlightLength()));

        if (flightRequest.getStatus() != null && flightRequest.getStatus().equals(FlightStatus.CANCELLED)) {
            flight.setDepartureTime(null);
            flight.setArrivalTime(null);
            flight.setStatus(FlightStatus.CANCELLED);
        } else if (weather.getWindSpeed() * 3.6 >= 30) {
            flight.setArrivalTime(flight.getArrivalTime().plusMinutes((long) (weather.getWindSpeed() * 3.6)));
            flight.setStatus(FlightStatus.DELAYED);
        } else {
            flight.setStatus(FlightStatus.ON_TIME);
        }

        return flight;
    }

    // Usuwa lot.
    public void deleteFlight(Long id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lot nie zostal znaleziony"));

        // Pierw usuwam pasazerow z danego lotu by nie powastaly problemy.
        removeFlightFromPassengers(flight.getFlightNumber());

        flightRepository.deleteById(id);
    }

    // Usuwa lot od pasazerow zanim usunie lot. Metoda zapobiega bledom bazy danych zwiazanych z integralnoscia danych.
    public void removeFlightFromPassengers(String flightNumber) {
        List<Passenger> passengers = passengerRepository.findByFlights_FlightNumber(flightNumber);

        System.out.println("Znaleziono " + passengers.size() + " pasażerów powiązanych z lotem o numerze: " + flightNumber);

        if (passengers.isEmpty()) {
            System.out.println("Brak pasażerów powiązanych z lotem o numerze: " + flightNumber);
        }

        for (Passenger passenger : passengers) {
            System.out.println("Przetwarzanie pasazera o ID: " + passenger.getId());

            System.out.println("Loty przed modyfikacja: ");
            passenger.getFlights().forEach(flight -> System.out.println(" - " + flight.getFlightNumber()));

            passenger.getFlights().removeIf(flight -> flight.getFlightNumber().equals(flightNumber));

            System.out.println("Loty po modyfikacji: ");
            passenger.getFlights().forEach(flight -> System.out.println(" - " + flight.getFlightNumber()));

            passengerRepository.save(passenger);
            System.out.println("Zapisano zmiany dla pasazera o ID: " + passenger.getId());
        }
    }
}
