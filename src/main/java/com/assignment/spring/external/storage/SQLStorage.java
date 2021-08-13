package com.assignment.spring.external.storage;

import com.assignment.spring.weather.WeatherStore;
import com.assignment.spring.weather.model.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SQLStorage implements WeatherStore {

    private final WeatherRepository weatherRepository;

    @Override
    public void save(Weather weather) {
        WeatherEntity entity = new WeatherEntity();
        entity.setCity(weather.getCity());
        entity.setCountry(weather.getCountry());
        entity.setTemperature(weather.getTemperature());

        weatherRepository.save(entity);
    }
}
