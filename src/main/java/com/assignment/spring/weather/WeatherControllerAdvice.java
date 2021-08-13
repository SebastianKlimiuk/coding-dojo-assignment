package com.assignment.spring.weather;

import com.assignment.spring.weather.api.exception.InternalServerException;
import com.assignment.spring.weather.api.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class WeatherControllerAdvice {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Void> handleNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InternalServerException.class})
    public ResponseEntity<Void> handleInternalException(InternalServerException ex) {
        log.error("Error has occurred during external api call", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
