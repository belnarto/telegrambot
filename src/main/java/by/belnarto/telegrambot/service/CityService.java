package by.belnarto.telegrambot.service;

import by.belnarto.telegrambot.model.City;
import by.belnarto.telegrambot.service.exception.CityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CityService {

    Optional<City> findById(Long id);

    Optional<City> findByCityName(String cityName);

    City save(City city);

    void delete(City city);

    City update(City city) throws CityNotFoundException;

    List<City> findAll();

}
