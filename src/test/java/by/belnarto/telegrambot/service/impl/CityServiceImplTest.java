package by.belnarto.telegrambot.service.impl;

import by.belnarto.telegrambot.model.City;
import by.belnarto.telegrambot.repository.CityRepository;
import by.belnarto.telegrambot.service.CityService;
import by.belnarto.telegrambot.service.exception.CityNotFoundException;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class CityServiceImplTest {

    @Autowired
    private CityService cityService;

    @MockBean
    private CityRepository cityRepository;

    @Before
    public void setUp() {
        City minsk = new City(1L, "Минск", "Город-герой.");
        City moscow = new City(2L, "Москва", "Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))");
        City london = new City(3L, "London", "London is the capital of Great Britain.");
        City gotham = new City(4L, "Gotham", "Gotham is under batman protection.");
        City testCity = new City(0L, "Test City", "Gotham is under batman protection");
        City testCityAfterSaving = new City(5L, "Test City", "Gotham is under batman protection");

        List<City> cities = Arrays.asList(moscow, london, gotham);

        Mockito.when(cityRepository.findById(1L))
                .thenReturn(Optional.of(minsk));
        Mockito.when(cityRepository.findById(2L))
                .thenReturn(Optional.of(minsk));
        Mockito.when(cityRepository.findById(5L))
                .thenReturn(Optional.of(testCityAfterSaving));
        Mockito.when(cityRepository.findByCityName("Москва"))
                .thenReturn(Optional.of(moscow));
        Mockito.doNothing().when(cityRepository).delete(minsk);
        Mockito.when(cityRepository.save(testCity))
                .thenReturn(testCityAfterSaving);
        Mockito.when(cityRepository.findAll())
                .thenReturn(cities);
    }

    @Test
    public void delete() {
        Optional<City> city = cityService.findById(1L);
        cityService.delete(city.orElseThrow(RuntimeException::new));
        List<City> cities = cityService.findAll();
        assertEquals(3, cities.size());
    }

    @Test
    public void findAll() {
        List<City> cities = cityService.findAll();
        assertEquals(3, cities.size());
    }

    @Test
    public void findById() {
        Optional<City> city = cityService.findById(2L);
        assertTrue(city.isPresent());
    }

    @Test
    public void findByCityName() {
        Optional<City> city = cityService.findByCityName("Москва");
        assertTrue(city.isPresent());
    }

    @Test(expected = CityNotFoundException.class)
    public void cityNotFound() throws CityNotFoundException {
        City city = new City(999L, "Test", "Test");
        city.setCityName("Sin city");
        cityService.update(city);
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
        Optional<City> optionalCity = cityService.findById(5L);
        City city = optionalCity.orElseThrow(RuntimeException::new);
        city.setCityName("Sin city");
        try {
            cityService.update(city);
        } catch (CityNotFoundException e) {
            fail();
        }
        optionalCity = cityService.findById(5L);
        assertEquals("Sin city", optionalCity.orElseThrow(RuntimeException::new).getCityName());
    }

}