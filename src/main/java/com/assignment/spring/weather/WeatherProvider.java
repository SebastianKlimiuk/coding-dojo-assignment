package com.assignment.spring.weather;

import com.assignment.spring.weather.model.Weather;

public interface WeatherProvider {
    Weather getTemperatureForCity(String city);
}
