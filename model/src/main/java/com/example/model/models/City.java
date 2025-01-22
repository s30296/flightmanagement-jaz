package com.example.model.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private Double latitude;
    private Double longitude;
}
