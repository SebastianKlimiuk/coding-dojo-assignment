package com.assignment.spring.weather.api.response;

import com.assignment.spring.weather.model.Weather;

public class DomainToResponseUtil {

    public static CityWeatherResponse translateDomainToResponse(Weather weather) {
        return new CityWeatherResponse(weather.getCity(), weather.getCountry(), weather.getTemperature());
    }
}
