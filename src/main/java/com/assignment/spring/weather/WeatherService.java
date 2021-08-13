package com.assignment.spring.weather;

import com.assignment.spring.weather.model.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherProvider weatherProvider;
    private final WeatherStore weatherStore;

    public Weather getWeatherForCity(String city) {
        Weather weatherForCity = weatherProvider.getTemperatureForCity(city);

        weatherStore.save(weatherForCity);

        return weatherForCity;
    }
}
