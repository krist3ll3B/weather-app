package com.challenge.sample.kristelle_weather_app.model;

public class Main {

    private double temp;

    public double getTemp() {
        return temp - 273.15; // Celsius = Kelvin - 273.15
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
}
