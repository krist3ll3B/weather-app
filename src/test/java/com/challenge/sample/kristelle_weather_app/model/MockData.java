package com.challenge.sample.kristelle_weather_app.model;

public class MockData {

    public static WeatherStackResult getWeatherStackResult() {

        Current current = new Current();
        current.setTemperature(22.75);
        current.setWind_speed(7.15);

        WeatherStackResult weatherStackResult = new WeatherStackResult();
        weatherStackResult.setCurrent(current);

        return weatherStackResult;
    }

    public static WeatherStackResult getWeatherStackNullCurrentResult() {

        WeatherStackResult weatherStackResult = new WeatherStackResult();
        weatherStackResult.setCurrent(null);

        return weatherStackResult;
    }

    public static Weather getWeatherResult(){
        Weather weather = new Weather();
        weather.setTemperature_degrees(20.22);
        weather.setWind_speed(5.18);
        return weather;
    }

    public static OpenWeatherResult getOpenWeatherResult(){
        Main main = new Main();
        main.setTemp(17.19);

        Wind wind = new Wind();
        wind.setSpeed(6.56);

        OpenWeatherResult openWeatherResult = new OpenWeatherResult();
        openWeatherResult.setMain(main);
        openWeatherResult.setWind(wind);

        return openWeatherResult;
    }

    public static WeatherCache getWeatherCache() {
        return new WeatherCache(getWeatherResult());
    }
}
