package com.assignment.spring;

import com.assignment.spring.api.Main;
import com.assignment.spring.api.Sys;
import com.assignment.spring.api.WeatherResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private WeatherRepository weatherRepository;

    @Test
    @DisplayName("given city name, expect 200 and entity saved to db")
    public void validCity() throws Exception {
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setName("Warsaw");
        Sys sys = new Sys();
        sys.setCountry("PL");
        weatherResponse.setSys(sys);
        Main main = new Main();
        main.setTemp(21.0);
        weatherResponse.setMain(main);
        when(restTemplate.getForEntity(anyString(), eq(WeatherResponse.class)))
                .thenReturn(new ResponseEntity<>(weatherResponse, HttpStatus.OK));

        WeatherEntity weatherEntity = new WeatherEntity();
        weatherEntity.setId(1);
        weatherEntity.setCity("Warsaw");
        weatherEntity.setCountry("PL");
        weatherEntity.setTemperature(21.0);
        when(weatherRepository.save(any())).thenReturn(weatherEntity);

        mockMvc.perform(get("/weather")
                        .param("city", "warsaw"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1, \"city\": \"Warsaw\", \"country\": \"PL\", \"temperature\": 21.0}"));
    }

    @Test
    @DisplayName("given invalid city name, expect 404")
    public void invalidCityName() throws Exception {
        when(restTemplate.getForEntity(anyString(), eq(WeatherResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/weather")
                        .param("city", "NotExistingCity"))
                .andExpect(status().isNotFound());
    }
}