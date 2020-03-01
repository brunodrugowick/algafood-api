package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.CityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CityCrudServiceIT {

    @Autowired
    private CityCrudService cityCrudService;

    @Autowired
    private ProvinceCrudService provinceCrudService;

    private Province province;

    @BeforeEach
    void setUp() {
        province = provinceCrudService.findOrElseThrow(1L);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveWithoutName() {
        City city = new City();
        city.setProvince(province);
        city.setName(null);

        assertThrows(ConstraintViolationException.class, () -> {
            cityCrudService.save(city);
        });
    }

    @Test
    void deleteNonExistent() {
        assertThrows(CityNotFoundException.class, () -> {
            cityCrudService.delete(-10L);
        });
    }
}