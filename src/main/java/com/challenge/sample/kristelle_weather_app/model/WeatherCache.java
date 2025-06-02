package com.challenge.sample.kristelle_weather_app.model;

public class WeatherCache {
    private final Weather weather;
    private final long timestamp;

    public WeatherCache(Weather weather) {
        this.weather = weather;
        this.timestamp = System.currentTimeMillis();
    }
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis() - this.timestamp;
        return currentTime > 3000;
    }

    public Weather getWeather() {
        return this.weather;
    }
}
