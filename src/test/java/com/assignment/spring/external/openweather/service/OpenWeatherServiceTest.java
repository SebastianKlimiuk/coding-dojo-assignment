package com.assignment.spring.external.openweather.service;

import com.assignment.spring.external.openweather.response.Main;
import com.assignment.spring.external.openweather.response.Sys;
import com.assignment.spring.external.openweather.response.WeatherResponse;
import com.assignment.spring.weather.api.exception.InternalServerException;
import com.assignment.spring.weather.api.exception.NotFoundException;
import com.assignment.spring.weather.model.Weather;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RestClientTest
class OpenWeatherServiceTest {

    private OpenWeatherService openWeatherService;
    private MockRestServiceServer mockServer;

    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    @BeforeEach
    public void beforeEach() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        when(restTemplateBuilder.errorHandler(any()))
                .thenReturn(restTemplateBuilder);
        when(restTemplateBuilder.errorHandler(any()).build())
                .thenReturn(restTemplate);
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();

        openWeatherService = new OpenWeatherService(restTemplateBuilder, "http://test.com", "someTestKeyString");
    }

    @Test
    @DisplayName("given city name, when api call succeeds, expect valid object")
    public void successCall() throws JsonProcessingException {
        String cityName = "Amsterdam";
        String country = "NL";
        double temperature = 23.1;

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setName(cityName);
        Sys sys = new Sys();
        sys.setCountry(country);
        weatherResponse.setSys(sys);
        Main main = new Main();
        main.setTemp(temperature);
        weatherResponse.setMain(main);

        ObjectMapper objectMapper = new ObjectMapper();
        String responseAsString = objectMapper.writeValueAsString(weatherResponse);

        mockServer.expect(requestTo("http://test.com?appid=someTestKeyString&q=" + cityName))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(responseAsString, MediaType.APPLICATION_JSON));

        Weather weather = openWeatherService.getTemperatureForCity(cityName);

        mockServer.verify();
        assertEquals(cityName, weather.getCity());
        assertEquals(country, weather.getCountry());
        assertEquals(temperature, weather.getTemperature());
    }

    @Test
    @DisplayName("given city name, when api call returns 401 Unauthorized, expect InternalServerException thrown")
    public void unauthorizedCall() {
        String cityName = "Amsterdam";

        mockServer.expect(requestTo("http://test.com?appid=someTestKeyString&q=" + cityName))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withUnauthorizedRequest());

        assertThrows(InternalServerException.class, () -> openWeatherService.getTemperatureForCity(cityName));

        mockServer.verify();
    }

    @Test
    @DisplayName("given non-existing city name, when api call returns empty body, expect NotFoundException thrown")
    public void emptyBodyReturned() {
        String cityName = "Amsterdam";

        mockServer.expect(requestTo("http://test.com?appid=someTestKeyString&q=" + cityName))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withNoContent());

        assertThrows(NotFoundException.class, () -> openWeatherService.getTemperatureForCity(cityName));

        mockServer.verify();
    }

    @Test
    @DisplayName("given non-existing city name, when api call returns 404 Not Found, expect NotFoundException thrown")
    public void cityNameNotFound() {
        String cityName = "Amsterdam";

        mockServer.expect(requestTo("http://test.com?appid=someTestKeyString&q=" + cityName))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        assertThrows(NotFoundException.class, () -> openWeatherService.getTemperatureForCity(cityName));

        mockServer.verify();
    }
}