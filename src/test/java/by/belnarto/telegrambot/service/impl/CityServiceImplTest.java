package by.belnarto.telegrambot.service.impl;

import by.belnarto.telegrambot.model.City;
import by.belnarto.telegrambot.service.CityService;
import by.belnarto.telegrambot.service.exception.CityNotFoundException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class CityServiceImplTest {

    @Autowired
    private CityService cityService;

    @Test
    public void delete() {
        Optional<City> city = cityService.findById(1L);
        cityService.delete(city.orElseThrow(RuntimeException::new));
        List<City> cities = cityService.findAll();
        assertEquals(cities.size(), 2);
    }

    @Test
    public void findAll() {
        List<City> cities = cityService.findAll();
        assertEquals(cities.size(), 2);
    }

    @Test
    public void findById() {
        Optional<City> city = cityService.findById(2L);
        assertNotNull(city.orElse(null));
    }

    @Test
    public void save() {
        City city = new City(0L, "Test City", "Gotham is under batman protection");
        city = cityService.save(city);
        Optional<City> optionalCity = cityService.findById(city.getId());
        assertNotNull(optionalCity.orElse(null));
    }

    @Test
    public void update() {
        Optional<City> optionalCity = cityService.findById(4L);
        City city = optionalCity.orElseThrow(RuntimeException::new);
        city.setCityName("Sin city");
        try {
            cityService.update(city);
        } catch (CityNotFoundException e) {
            fail();
        }
        optionalCity = cityService.findById(4L);
        assertEquals(optionalCity.orElseThrow(RuntimeException::new).getCityName(), "Sin city");
    }

}