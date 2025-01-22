package com.example.model.repositories;

import com.example.model.models.Flight;
import com.example.model.models.FlightStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByStatus(FlightStatus status);

    Flight findByFlightNumber(String flightNumber);
}
