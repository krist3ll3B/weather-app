package com.challenge.sample.kristelle_weather_app.controller;

import com.challenge.sample.kristelle_weather_app.model.Weather;
import com.challenge.sample.kristelle_weather_app.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        return e.getMessage();
    }

    @GetMapping("/v1/weather")
    public ResponseEntity<Weather> getWeather(@RequestParam(value = "city", required = false) String city) {
        if (city == null) {
            city = "melbourne";
        }
        return new ResponseEntity<>(weatherService.getWeather(city), HttpStatus.OK);
    }

}
