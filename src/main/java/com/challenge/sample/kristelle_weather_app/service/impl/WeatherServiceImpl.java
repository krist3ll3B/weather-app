package com.challenge.sample.kristelle_weather_app.service.impl;

import com.challenge.sample.kristelle_weather_app.model.OpenWeatherResult;
import com.challenge.sample.kristelle_weather_app.model.Weather;
import com.challenge.sample.kristelle_weather_app.model.WeatherCache;
import com.challenge.sample.kristelle_weather_app.model.WeatherStackResult;
import com.challenge.sample.kristelle_weather_app.service.OpenWeatherMapProviderService;
import com.challenge.sample.kristelle_weather_app.service.WeatherService;
import com.challenge.sample.kristelle_weather_app.service.WeatherStackProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final OpenWeatherMapProviderService openWeatherMapProviderService;
    private final WeatherStackProviderService weatherStackProviderService;

    private WeatherCache weatherCache;
    @Autowired
    public WeatherServiceImpl(OpenWeatherMapProviderService openWeatherMapProviderService,
                              WeatherStackProviderService weatherStackProviderService) {
        this.openWeatherMapProviderService = openWeatherMapProviderService;
        this.weatherStackProviderService = weatherStackProviderService;
    }

    @Override
    public synchronized Weather getWeather(String city) {

        // verify cache; cached for up to 3 seconds
        if(weatherCache != null && !weatherCache.isExpired()) {
            return weatherCache.getWeather();
        }

        // primary provider
        Weather weather = callPrimaryProvider(city);

        if(weather == null) {
            // secondary provider
            weather = callFailOverProvider(city);
        }

        return handleCache(weather);
    }

    private Weather callPrimaryProvider(String city) {

        WeatherStackResult weatherStack;
        weatherStack = weatherStackProviderService.getWeather(city);

        if(weatherStack != null) {
            Weather weather = new Weather();
            weather.setTemperature_degrees(weatherStack.getCurrent().getTemperature());
            weather.setWind_speed(weatherStack.getCurrent().getWind_speed());

            return weather;
        } else {
            return null;
        }
    }

    private Weather callFailOverProvider(String city) {

        // failover provider
        OpenWeatherResult openWeather;
        openWeather = openWeatherMapProviderService.getWeather(city);

        if(openWeather != null) {
            Weather weather = new Weather();
            weather.setTemperature_degrees(openWeather.getMain().getTemp());
            weather.setWind_speed(openWeather.getWind().getSpeed());

            return weather;
        } else {
            return null;
        }
    }

    private Weather handleCache(Weather weather) {

        if(weather != null) {
            // update cache;
            weatherCache = new WeatherCache(weather);
        } else if (weatherCache != null) {
            // serve stale cache if providers fail
            return weatherCache.getWeather();
        } else {
            throw new RuntimeException("All weather providers are down and no cache available");
        }
        return weather;
    }
}
