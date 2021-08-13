package com.assignment.spring.external.openweather.service;

import com.assignment.spring.external.openweather.constants.RequestParams;
import com.assignment.spring.external.openweather.response.WeatherResponse;
import com.assignment.spring.weather.WeatherProvider;
import com.assignment.spring.weather.api.exception.NotFoundException;
import com.assignment.spring.weather.model.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OpenWeatherService implements WeatherProvider {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;


    public OpenWeatherService(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${openweather.api.url}") String apiUrl,
            @Value("${openweather.api.key}") String apiKey
    ) {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    @Override
    public Weather getTemperatureForCity(String city) {
        String url = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam(RequestParams.API_KEY.getParamName(), apiKey)
                .queryParam(RequestParams.CITY.getParamName(), city)
                .toUriString();

        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);

        if (response.getBody() == null) {
            throw new NotFoundException();
        }

        return DomainMapper.translateToDomain(response.getBody());
    }
}
