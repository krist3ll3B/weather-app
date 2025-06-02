package com.challenge.sample.kristelle_weather_app.controller;

import com.challenge.sample.kristelle_weather_app.model.MockData;
import com.challenge.sample.kristelle_weather_app.model.Weather;
import com.challenge.sample.kristelle_weather_app.service.WeatherService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.challenge.sample.kristelle_weather_app.model.MockData.getWeatherResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    private MockMvc mockMvc;

    private WeatherController weatherController;
    private static final String CITY = "melbourne";;

    @Before
    public void setup(){
        weatherController = new WeatherController(weatherService);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    public void shouldReturnResponseBodyContainingWeatherDataTest() throws Exception {

        Weather expected = getWeatherResult();

        when(weatherService.getWeather(CITY)).thenReturn(expected);

        ResponseEntity<?> result = weatherController.getWeather(CITY);

        assertThat(result).isNotNull();
        assertThat(result.getBody()).isEqualTo(expected);

        mockMvc.perform(get("/v1/weather?city=melbourne"))
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnErrorMessageIfThereIsExceptionTest() throws Exception {
        when(weatherService.getWeather(CITY)).thenThrow(new RuntimeException("error"));

        mockMvc.perform(get("/v1/weather?city=melbourne"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("error"));


    }
}
