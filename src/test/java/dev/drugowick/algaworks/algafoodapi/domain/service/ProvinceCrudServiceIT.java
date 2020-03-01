package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProvinceCrudServiceIT {

    @Autowired
    private CityCrudService cityCrudService;

    @Autowired
    private ProvinceCrudService provinceCrudService;

    private City city;
    private Province province;

    @BeforeEach
    void setUp() {
        city = cityCrudService.findOrElseThrow(1L);
        province = city.getProvince();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteInUse() {
        assertThrows(EntityBeingUsedException.class, () -> {
            provinceCrudService.delete(province.getId());
        });
    }

    @Test
    void findOrElseThrow() {
    }

}