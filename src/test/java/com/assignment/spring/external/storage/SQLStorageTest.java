package com.assignment.spring.external.storage;

import com.assignment.spring.weather.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SQLStorageTest {

    @Autowired
    private WeatherRepository weatherRepository;

    private SQLStorage sqlStorage;

    @BeforeEach
    public void beforeEach() {
        sqlStorage = new SQLStorage(weatherRepository);
    }

    @Test
    @DisplayName("given domain model, when saving to storage, expect entity saved to storage")
    public void saveValidModel() {
        Weather weather = new Weather("Amsterdam", "NL", 23.1);

        sqlStorage.save(weather);

        Optional<WeatherEntity> pulledEntity = weatherRepository.findById(1);

        assertTrue(pulledEntity.isPresent());
        assertEquals("Amsterdam", pulledEntity.get().getCity());
        assertEquals("NL", pulledEntity.get().getCountry());
        assertEquals(23.1, pulledEntity.get().getTemperature());
    }
}