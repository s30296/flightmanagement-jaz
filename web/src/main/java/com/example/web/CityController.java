package com.example.web;

import com.example.model.models.City;
import com.example.service.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /* Metoda POST tworzaca miasto.
    Przyklad JSON:
    {
        "name": "Warsaw",
        "country": "Poland",
        "latitude": 52.2297,
        "longitude": 21.0122
    }
    */
    @PostMapping("/create")
    public ResponseEntity<City> createCity(@RequestBody City city) {
        return ResponseEntity.ok(cityService.createCity(city));
    }

    // Metoda GET zwracajaca liste wszystkich miast.
    @GetMapping("/getAllCities")
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    // Metoda GET zwracajaca liste wszystkich miast w cache.
    @GetMapping("/getAllCachedCities")
    public ResponseEntity<List<City>> getAllCachedCities() {
        return ResponseEntity.ok(cityService.getAllCachedCities());
    }

    // Metoda GET zwracajaca miasto za pomoca id.
    @GetMapping("/getCityById/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(cityService.getCityById(id));
    }
}
