package com.assignment.spring.weather.api.exception;

public class InternalServerException extends RuntimeException {

    public InternalServerException() {
        super();
    }

    public InternalServerException(String message) {
        super(message);
    }
}
