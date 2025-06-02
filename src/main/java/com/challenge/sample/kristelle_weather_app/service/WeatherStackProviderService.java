package com.challenge.sample.kristelle_weather_app.service;

import com.challenge.sample.kristelle_weather_app.model.WeatherStackResult;

public interface WeatherStackProviderService {

    WeatherStackResult getWeather(String city);
}
