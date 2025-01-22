package com.example.model.repositories;

import com.example.model.models.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    Optional<Weather> findByCityName(String cityName);
    Optional<Weather> findTopByCityNameOrderByTimestampDesc(String cityName);
    void deleteAll();
}
