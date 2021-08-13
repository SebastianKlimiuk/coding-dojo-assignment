package com.assignment.spring;

import com.assignment.spring.weather.WeatherProvider;
import com.assignment.spring.weather.api.exception.InternalServerException;
import com.assignment.spring.weather.api.exception.NotFoundException;
import com.assignment.spring.weather.model.Weather;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherProvider weatherProvider;

    @Test
    @DisplayName("given city name, expect 200 and entity saved to db")
    public void validCity() throws Exception {
        String cityName = "Amsterdam";
        when(weatherProvider.getTemperatureForCity(cityName)).thenReturn(new Weather(cityName, "NL", 23.0));

        mockMvc.perform(get("/weather")
                        .param("city", cityName))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"city\": \"" + cityName + "\", \"country\": \"NL\", \"temperature\": 23.0}"));
    }

    @Test
    @DisplayName("given invalid city name, expect 404")
    public void invalidCityName() throws Exception {
        when(weatherProvider.getTemperatureForCity(anyString())).thenThrow(new NotFoundException());

        mockMvc.perform(get("/weather")
                        .param("city", "NotExistingCity"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("given city name, when weather api throws exception other than not found, expect 500")
    public void weatherApiUnauthorized() throws Exception {
        when(weatherProvider.getTemperatureForCity(anyString())).thenThrow(new InternalServerException());

        mockMvc.perform(get("/weather")
                        .param("city", "Amsterdam"))
                .andExpect(status().isInternalServerError());
    }
}