package com.assignment.spring.weather;

import com.assignment.spring.weather.model.Weather;

public interface WeatherStore {
    void save(Weather weather);
}
