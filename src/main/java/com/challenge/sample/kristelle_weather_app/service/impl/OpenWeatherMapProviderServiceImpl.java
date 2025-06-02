package com.challenge.sample.kristelle_weather_app.service.impl;

import com.challenge.sample.kristelle_weather_app.model.OpenWeatherResult;
import com.challenge.sample.kristelle_weather_app.service.OpenWeatherMapProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenWeatherMapProviderServiceImpl implements OpenWeatherMapProviderService {

    private final RestTemplate restTemplate;
    private final String openWeatherMapUrl;

    public OpenWeatherMapProviderServiceImpl(RestTemplate restTemplate,
                                             @Value("${open.weather.url}") String openWeatherMapUrl) {
        this.restTemplate = restTemplate;
        this.openWeatherMapUrl = openWeatherMapUrl;
    }

    @Override
    public OpenWeatherResult getWeather(String city) {
        String url = String.format(openWeatherMapUrl, city);

        try {
            ResponseEntity<OpenWeatherResult> response = restTemplate.getForEntity(url, OpenWeatherResult.class);

            if(response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                return response.getBody();
            } else {
                System.out.printf("Unable to fetch weather data from Open Weather Map; return code is %s%n", response.getStatusCode());
                return null;
            }
        } catch (HttpClientErrorException.Unauthorized e) {
            System.out.printf("Unable to fetch weather data from Open Weather Map; %s", e.getMessage());
        } catch (RestClientException e) {
            System.out.printf("Unable to fetch weather data from Open Weather Map due to %s", e);
        }

        return null;
    }
}
