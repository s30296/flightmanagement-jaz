package com.example.service.services;

import com.example.model.models.City;
import com.example.model.repositories.CityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    // Tworzy miasto.
    public City createCity(City city) {
        return cityRepository.save(city);
    }

    // Wyswietla liste wszystkich miast.
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    // Wyswietla liste wszystkich miast z cache.
    @Cacheable(value = "getAllCachedCities")
    public List<City> getAllCachedCities() {
        log.info("getAllCities zostanie teraz zapisane w cache, nastepne wywolanie nie wyswietli tej wiadomosci i dane zostana wyswietlone z cache");
        return cityRepository.findAll();
    }

    // Wyswietla miasto po id.
    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nie znaleziono miasta z id: " + id));
    }
}
