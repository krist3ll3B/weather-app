package com.challenge.sample.kristelle_weather_app.service.impl;

import com.challenge.sample.kristelle_weather_app.model.OpenWeatherResult;
import com.challenge.sample.kristelle_weather_app.model.WeatherStackResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static com.challenge.sample.kristelle_weather_app.model.MockData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class WeatherStackProviderServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    private WeatherStackProviderServiceImpl weatherStackProviderServiceImpl;

    private static final String URL = "http://test.com/api/";
    private static final String CITY = "melbourne";

    @Before
    public void setup() {
        weatherStackProviderServiceImpl = new WeatherStackProviderServiceImpl(restTemplate, URL);
    }

    @Test
    public void shouldReturnValidDataFromWeatherStackTest(){

        WeatherStackResult expected = getWeatherStackResult();

        ResponseEntity<Object> response = new ResponseEntity<>(expected, HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any())).thenReturn(response);

        WeatherStackResult result = weatherStackProviderServiceImpl.getWeather(CITY);

        assertThat(result).isNotNull();
        assertThat(expected).isEqualTo(result);

    }

    @Test
    public void shouldReturnNullWhenApiIsNotAvailableTest() {
        ResponseEntity<Object> response = new ResponseEntity<>(null, HttpStatus.SERVICE_UNAVAILABLE);

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any())).thenReturn(response);

        WeatherStackResult result = weatherStackProviderServiceImpl.getWeather(CITY);

        assertThat(result).isNull();

    }

    @Test
    public void shouldReturnNullWhenApiResponseIsNotValidTest(){
        ResponseEntity<Object> response = new ResponseEntity<>(getWeatherStackNullCurrentResult(), HttpStatus.OK);

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any())).thenReturn(response);

        WeatherStackResult result = weatherStackProviderServiceImpl.getWeather(CITY);

        assertThat(result).isNull();
    }

    @Test
    public void shouldHandleRestClientExceptionTest() {

        Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Mockito.any()))
                .thenThrow(new RestClientException("error"));

        WeatherStackResult result = weatherStackProviderServiceImpl.getWeather(CITY);

        assertThat(result).isNull();
    }

}
