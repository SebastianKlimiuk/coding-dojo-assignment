package com.assignment.spring.weather.model;

import lombok.Value;

@Value
public class Weather {

    String city;
    String country;
    Double temperature;
}
