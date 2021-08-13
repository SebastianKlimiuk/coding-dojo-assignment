package com.assignment.spring.weather;

import com.assignment.spring.weather.api.response.CityWeatherResponse;
import com.assignment.spring.weather.api.response.DomainToResponseUtil;
import com.assignment.spring.weather.model.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<CityWeatherResponse> weather(@RequestParam String city) {
        Weather weatherForCity = weatherService.getWeatherForCity(city);

        return ResponseEntity.ok(DomainToResponseUtil.translateDomainToResponse(weatherForCity));
    }

}
