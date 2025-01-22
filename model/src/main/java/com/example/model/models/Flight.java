package com.example.model.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id")
    private Airport departureAirport;

    @ManyToOne
    @JoinColumn(name = "arrival_airport_id", referencedColumnName = "id")
    private Airport arrivalAirport;

    @ManyToOne
    @JoinColumn(name = "weather_id", referencedColumnName = "id")
    private Weather weather;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlightStatus status;

    public Flight(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @ManyToMany(mappedBy = "flights", cascade = CascadeType.PERSIST)
    private List<Passenger> passengers;
}
