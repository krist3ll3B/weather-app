package com.challenge.sample.kristelle_weather_app.service.impl;

import com.challenge.sample.kristelle_weather_app.model.WeatherStackResult;
import com.challenge.sample.kristelle_weather_app.service.WeatherStackProviderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherStackProviderServiceImpl implements WeatherStackProviderService {

    private final RestTemplate restTemplate;
    private final String weatherStackURL;

    public WeatherStackProviderServiceImpl(RestTemplate restTemplate,
                                           @Value("${weather.stack.url}") String weatherStackURL) {
        this.restTemplate = restTemplate;
        this.weatherStackURL = weatherStackURL;
    }

    @Override
    public WeatherStackResult getWeather(String city) {
        String url = String.format(weatherStackURL, city);
        ResponseEntity<WeatherStackResult> response;

        try {
            response = restTemplate.getForEntity(url, WeatherStackResult.class);
            if(response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null && response.getBody().getCurrent() != null) {
                return response.getBody();
            } else {
                System.out.printf("Unable to fetch weather data from Weather Stack; return code is %s and body is empty%n", response.getStatusCode());
                return null;
            }

        } catch(RestClientException e) {
            System.out.printf("Unable to fetch weather data from Weather Stack due to %s", e);
        }
        return null;
    }
}
