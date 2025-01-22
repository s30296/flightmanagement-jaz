package com.example.web;

import com.example.model.models.Passenger;
import com.example.service.dto.PassengerRequest;
import com.example.service.dto.PassengerResponse;
import com.example.service.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    // Metoda GET zwracajaca wszystkich pasazerow.
    @GetMapping("/getAllPassengers")
    public List<Passenger> getAllPassengers() {
        return passengerService.getAllPassengers();
    }

    // Metoda GET zwracajaca konkretnych pasazerow za pomoca ich id.
    @GetMapping("/getPassengerById/{id}")
    public Passenger getPassengerById(@PathVariable Long id) {
        return passengerService.getPassengerById(id);
    }

    // Metoda GET zwracajaca liste pasazerow danego lotu za pomoca jego numeru.
    @GetMapping("/flight/{flightNumber}")
    public List<Passenger> getPassengersByFlightNumber(@PathVariable String flightNumber) {
        return passengerService.getPassengersByFlightNumber(flightNumber);
    }

    // Metoda GET zwracajaca liste pasazerow danego lotu za pomoca jego numeru.
    @GetMapping("/flightCached/{flightNumber}")
    public List<Passenger> getCachedPassengersByFlightNumber(@PathVariable String flightNumber) {
        return passengerService.getCachedPassengersByFlightNumber(flightNumber);
    }

    /* Metoda POST tworzaca nowego pasazera
    {
        "email": "jackblack@gmail.com",
        "firstName": "Jack",
        "lastName": "Black",
        "flights": [1, 2]
    }
    */
    @PostMapping("/create")
    public PassengerResponse createPassenger(@RequestBody PassengerRequest passengerRequest) {
        return passengerService.createPassenger(passengerRequest);
    }

    /* Metoda PUT modyfikujaca/aktualizujaca istniejacego juz pasazera, mozna edytowac tylko poszczegolne kolumny.
    Przykladowy JSON dla http://localhost:8080/passengers/update/1:
    {
        "firstName": "Jack",
        "flights": [1, 2]
    }
    Jak widac modyfikuje dwie kolumny, a nie wszystko jesli nie potrzeba.
    */
    @PutMapping("/update/{id}")
    public PassengerResponse updatePassenger(@PathVariable Long id, @RequestBody PassengerRequest passengerRequest) {
        return passengerService.updatePassenger(id, passengerRequest);
    }

    // Metoda DELETE usuwajaca pasazera z bazy danych.
    @DeleteMapping("/delete/{id}")
    public void deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
    }
}
