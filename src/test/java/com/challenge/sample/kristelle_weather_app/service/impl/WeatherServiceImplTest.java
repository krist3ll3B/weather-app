package com.challenge.sample.kristelle_weather_app.service.impl;

import com.challenge.sample.kristelle_weather_app.model.*;
import com.challenge.sample.kristelle_weather_app.service.OpenWeatherMapProviderService;
import com.challenge.sample.kristelle_weather_app.service.WeatherStackProviderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

import static com.challenge.sample.kristelle_weather_app.model.MockData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceImplTest {

    @Mock
    private OpenWeatherMapProviderService mockOpenWeatherService;
    @Mock
    private WeatherStackProviderService mockWeatherStackService;

    private WeatherServiceImpl weatherServiceImpl;

    private static final String CITY = "melbourne";


    @Before
    public void setup() {
        weatherServiceImpl = new WeatherServiceImpl(mockOpenWeatherService, mockWeatherStackService);
    }

    @Test
    public void shouldFetchWeatherFromPrimaryProviderTest() throws NoSuchFieldException, IllegalAccessException {

        WeatherStackResult expected = getWeatherStackResult();

        Mockito.when(mockWeatherStackService.getWeather(Mockito.anyString())).thenReturn(expected);

        Weather result = weatherServiceImpl.getWeather(CITY);

        assertThat(result).isNotNull();

        Field cacheField = WeatherServiceImpl.class.getDeclaredField("weatherCache");
        cacheField.setAccessible(true);
        WeatherCache weatherCache = (WeatherCache) cacheField.get(weatherServiceImpl);

        assertThat(weatherCache).isNotNull();
        // verify that the initial value matches the cached value
        assertThat(expected.getCurrent().getWind_speed()).isEqualTo(weatherCache.getWeather().getWind_speed());
        assertThat(expected.getCurrent().getTemperature()).isEqualTo(weatherCache.getWeather().getTemperature_degrees());
        // verify that the result matches the cached value
        assertThat(result.getTemperature_degrees()).isEqualTo(weatherCache.getWeather().getTemperature_degrees());
        assertThat(result.getWind_speed()).isEqualTo(weatherCache.getWeather().getWind_speed());
    }

    @Test
    public void shouldFetchWeatherFromSecondaryProviderTest() throws NoSuchFieldException, IllegalAccessException {

        OpenWeatherResult expected = getOpenWeatherResult();

        Mockito.when(mockWeatherStackService.getWeather(Mockito.anyString())).thenReturn(null);
        Mockito.when(mockOpenWeatherService.getWeather(Mockito.anyString())).thenReturn(expected);

        Weather result = weatherServiceImpl.getWeather(CITY);

        verify(mockWeatherStackService, times(1)).getWeather(anyString());
        assertThat(result).isNotNull();

        Field cacheField = WeatherServiceImpl.class.getDeclaredField("weatherCache");
        cacheField.setAccessible(true);
        WeatherCache weatherCache = (WeatherCache) cacheField.get(weatherServiceImpl);

        assertThat(weatherCache).isNotNull();
        // verify that the initial value matches the cached value
        assertThat(expected.getWind().getSpeed()).isEqualTo(weatherCache.getWeather().getWind_speed());
        assertThat(expected.getMain().getTemp()).isEqualTo(weatherCache.getWeather().getTemperature_degrees());
        // verify that the result matches the cached value
        assertThat(result.getTemperature_degrees()).isEqualTo(weatherCache.getWeather().getTemperature_degrees());
        assertThat(result.getWind_speed()).isEqualTo(weatherCache.getWeather().getWind_speed());

    }


    @Test
    public void shouldFetchWeatherFromCacheTest() throws NoSuchFieldException, IllegalAccessException {

        WeatherCache weatherCache = getWeatherCache();

        Field timestampField = WeatherCache.class.getDeclaredField("timestamp");
        timestampField.setAccessible(true);
        timestampField.setLong(weatherCache, System.currentTimeMillis() - 1);

        Field cacheField = WeatherServiceImpl.class.getDeclaredField("weatherCache");
        cacheField.setAccessible(true);
        cacheField.set(weatherServiceImpl, weatherCache);

        Weather result = weatherServiceImpl.getWeather(CITY);

        verify(mockWeatherStackService, never()).getWeather(anyString());
        assertThat(result).isSameAs(weatherCache.getWeather());

    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionTest(){

        Mockito.when(mockWeatherStackService.getWeather(Mockito.anyString())).thenReturn(null);
        Mockito.when(mockOpenWeatherService.getWeather(Mockito.anyString())).thenReturn(null);

        weatherServiceImpl.getWeather(CITY);

        verify(mockWeatherStackService, times(1)).getWeather(anyString());
        verify(mockOpenWeatherService, times(1)).getWeather(anyString());

    }

    @Test
    public void shouldFetchWeatherFromStaleCacheTest() throws NoSuchFieldException, IllegalAccessException {

        WeatherCache weatherCache = getWeatherCache();

        Field timestampField = WeatherCache.class.getDeclaredField("timestamp");
        timestampField.setAccessible(true);
        timestampField.setLong(weatherCache, System.currentTimeMillis() - 5000); //expired

        Field cacheField = WeatherServiceImpl.class.getDeclaredField("weatherCache");
        cacheField.setAccessible(true);
        cacheField.set(weatherServiceImpl, weatherCache);

        Mockito.when(mockWeatherStackService.getWeather(Mockito.anyString())).thenReturn(null);
        Mockito.when(mockOpenWeatherService.getWeather(Mockito.anyString())).thenReturn(null);

        Weather result = weatherServiceImpl.getWeather(CITY);

        verify(mockWeatherStackService, times(1)).getWeather(anyString());
        verify(mockOpenWeatherService, times(1)).getWeather(anyString());

        assertThat(result).isSameAs(weatherCache.getWeather());
    }

}
