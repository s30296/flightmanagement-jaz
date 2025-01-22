package com.example.web;

import com.example.model.models.Weather;
import com.example.service.dto.WeatherApiRequest;
import com.example.service.dto.WeatherRequest;
import com.example.service.dto.WeatherResponse;
import com.example.service.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // Metoda GET zwracajaca liste wszystkich rekordow pogodowych miast.
    @GetMapping("/getAllWeather")
    public List<WeatherResponse> getAllWeather() {
        return weatherService.getAllWeather();
    }

    // Metoda GET zwracajaca najnowsze informacje dotyczace pogody zapisane w lokalnej bazie danych.
    @GetMapping("/getLatestWeather/{city}")
    public WeatherResponse getLatestWeather(@PathVariable String city) {
        return weatherService.getLatestWeatherByCity(city);
    }

    // Metoda GET przyjmujaca zapytanie w postaci PathVariable, zwracajaca aktualne dane pogodowe miasta za pomoca API.
    @GetMapping("/{city}")
    public Weather getWeather(@PathVariable String city) {
        return weatherService.getWeatherByCityFromApi(city);
    }

    // Metoda POST przyjmujaca zapytanie w postaci RequestBody, zwracajaca dane z lokalnej bazy danych za pomoca id rekordu.
    @PostMapping("/getWeatherById")
    public WeatherResponse getWeatherById(@RequestBody WeatherRequest request) {
        return weatherService.getWeatherById(request);
    }

    /* Metoda POST przyjmujaca zapytanie w postaci RequestBody, zwracajaca aktualne dane pogodowe miasta za pomoca API.
    Aby moc wywolac ten endpoint w Headers POST nalezy dodac Key = Content-Type, Value = application/json w celu mozliwosci dodania w Body
    przykladowej wartosci np.
    {
        "cityName": "Warsaw"
    }
    */
    @PostMapping("/fetch")
    public Weather getWeatherByRequest(@RequestBody WeatherApiRequest request) {
        return weatherService.getWeatherByCityFromApi(request.getCityName());
    }

    // Metoda DELETE usuwajaca wszystkie rekordy pogodowe z lokalnej bazy danych.
    @DeleteMapping("/deleteAllWeather")
    public ResponseEntity<String> deleteAllWeather() {
        weatherService.deleteAllWeather();
        return ResponseEntity.ok("Wszystkie rekordy pogodowe znajdujace sie w lokalnej bazie danych zostaly usuniete.");
    }
}
