package by.belnarto.telegrambot.service.impl;

import by.belnarto.telegrambot.model.City;
import by.belnarto.telegrambot.repository.CityRepository;
import by.belnarto.telegrambot.service.CityService;
import by.belnarto.telegrambot.service.exception.CityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CityServiceImpl implements CityService {

    private CityRepository cityRepository;

    public CityServiceImpl(final CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public Optional<City> findById(final Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public City save(final City city) {
        return cityRepository.save(city);
    }

    @Override
    public void delete(final City city) {
        cityRepository.delete(city);
    }

    //TODO refactor
    @Override
    public City update(final City updatedCity) throws CityNotFoundException {
        City city = cityRepository.findById(updatedCity.getId()).orElseThrow(CityNotFoundException::new);
        city.setCityName(updatedCity.getCityName());
        city.setCityInfo(updatedCity.getCityInfo());
        return city;
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
