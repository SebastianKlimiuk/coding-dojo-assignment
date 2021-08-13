package com.assignment.spring.external.openweather.service;

import com.assignment.spring.external.openweather.response.WeatherResponse;
import com.assignment.spring.weather.model.Weather;

import static java.util.Objects.isNull;

public class DomainMapper {

    public static Weather translateToDomain(WeatherResponse response) {
        String country = null;
        if (!isNull(response.getSys())) {
            country = response.getSys().getCountry();
        }
        Double temperature = null;
        if (!isNull(response.getMain())) {
            temperature = response.getMain().getTemp();
        }

        return new Weather(response.getName(), country, temperature);
    }
}
