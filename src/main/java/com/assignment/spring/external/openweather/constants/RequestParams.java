package com.assignment.spring.external.openweather.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RequestParams {
    CITY("q"),
    API_KEY("appid");

    private final String paramName;
}
