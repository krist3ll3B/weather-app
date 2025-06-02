package com.challenge.sample.kristelle_weather_app.service;

import com.challenge.sample.kristelle_weather_app.model.OpenWeatherResult;

public interface OpenWeatherMapProviderService {
    OpenWeatherResult getWeather(String city);
}
