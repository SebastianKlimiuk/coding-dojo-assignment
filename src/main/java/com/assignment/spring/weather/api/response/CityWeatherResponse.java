package com.assignment.spring.weather.api.response;

import lombok.Value;

@Value
public class CityWeatherResponse {

    String city;
    String country;
    double temperature;
}
