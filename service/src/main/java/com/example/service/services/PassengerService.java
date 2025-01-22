package com.example.service.services;

import com.example.model.models.Flight;
import com.example.model.models.FlightStatus;
import com.example.model.models.Passenger;
import com.example.model.repositories.PassengerRepository;
import com.example.model.repositories.FlightRepository;
import com.example.service.dto.PassengerRequest;
import com.example.service.dto.PassengerResponse;
import com.example.service.mappers.PassengerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private final PassengerRepository passengerRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private PassengerMapper passengerMapper;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    // Zwraca wszystkich pasazerow znajdujacych sie w bazie danych.
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    // Zwraca dane o pasazerze na podstawie jego id.
    public Passenger getPassengerById(Long id) {
        return passengerRepository.findById(id).orElse(null);
    }

    // Zwraca liste pasazerow na podstawie numeru lotu.
    public List<Passenger> getPassengersByFlightNumber(String flightNumber) {
        return passengerRepository.findByFlights_FlightNumber(flightNumber);
    }

    // Zwraca liste pasazerow na podstawie numeru lotu w cache.
    @Cacheable(value = "getCachedPassengersByFlightNumber")
    public List<Passenger> getCachedPassengersByFlightNumber(String flightNumber) {
        return passengerRepository.findByFlights_FlightNumber(flightNumber);
    }

    // Tworzy nowego pasazera, mozna podac bez lotu
    public PassengerResponse createPassenger(PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toPassenger(passengerRequest);

        if (passengerRequest.getFlights() != null && !passengerRequest.getFlights().isEmpty()) {
            List<Flight> flights = flightRepository.findAllById(passengerRequest.getFlights());

            for (Flight flight : flights) {
                if (flight.getStatus() == FlightStatus.CANCELLED) {
                    throw new IllegalArgumentException("Nie mozna dodac pasazera do lotu, ktory jest odwolany");
                }
            }

            passenger.setFlights(flights);
        } else {
            passenger.setFlights(null);
        }

        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toPassengerResponse(savedPassenger);
    }

    // Aktualizuje istniejacego juz pasazera, mozna edytowac tylko poszczegolne kolumny.
    public PassengerResponse updatePassenger(Long id, PassengerRequest passengerRequest) {
        Passenger existingPassenger = passengerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pasazer nie zostal znaleziony"));

        if (passengerRequest.getEmail() != null) {
            existingPassenger.setEmail(passengerRequest.getEmail());
        }
        if (passengerRequest.getFirstName() != null) {
            existingPassenger.setFirstName(passengerRequest.getFirstName());
        }
        if (passengerRequest.getLastName() != null) {
            existingPassenger.setLastName(passengerRequest.getLastName());
        }

        if (passengerRequest.getFlights() != null && !passengerRequest.getFlights().isEmpty()) {
            List<Flight> flights = flightRepository.findAllById(passengerRequest.getFlights());

            for (Flight flight : flights) {
                if (flight.getStatus() == FlightStatus.CANCELLED) {
                    throw new IllegalArgumentException("Nie mozna dodac pasazera do lotu, ktory jest odwolany");
                }
            }

            existingPassenger.setFlights(flights);
        }

        Passenger updatedPassenger = passengerRepository.save(existingPassenger);
        return passengerMapper.toPassengerResponse(updatedPassenger);
    }

    // Usuwa pasazera z bazy danych.
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
}
