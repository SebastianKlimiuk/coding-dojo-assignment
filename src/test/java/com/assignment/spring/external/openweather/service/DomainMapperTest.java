package com.assignment.spring.external.openweather.service;

import com.assignment.spring.external.openweather.response.Main;
import com.assignment.spring.external.openweather.response.Sys;
import com.assignment.spring.external.openweather.response.WeatherResponse;
import com.assignment.spring.weather.model.Weather;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class DomainMapperTest {

    @Test
    @DisplayName("given openWeather API response with non-null fields, expect domain model with same fields")
    public void nonNullFieldsInApiResponse() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setName("Amsterdam");
        Sys sys = new Sys();
        sys.setCountry("NL");
        weatherResponse.setSys(sys);
        Main main = new Main();
        main.setTemp(23.1);
        weatherResponse.setMain(main);

        Weather domain = DomainMapper.translateToDomain(weatherResponse);

        assertEquals("Amsterdam", domain.getCity());
        assertEquals("NL", domain.getCountry());
        assertEquals(23.1, domain.getTemperature());
    }

    @Test
    @DisplayName("given openWeather API response with null fields, expect domain model with nulls")
    public void nullFieldsInApiResponse() {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setName("Amsterdam");

        Weather domain = DomainMapper.translateToDomain(weatherResponse);

        assertEquals("Amsterdam", domain.getCity());
        assertNull(domain.getCountry());
        assertNull(domain.getTemperature());
    }
}