package by.belnarto.telegrambot.repository;

import by.belnarto.telegrambot.model.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    public void findByCityName() {
        City minsk = new City(1L, "Минск", "Город-герой.");
        City moscow = new City(2L, "Москва", "Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))");
        City london = new City(3L, "London", "London is the capital of Great Britain.");
        City gotham = new City(4L, "Gotham", "Gotham is under batman protection.");
        cityRepository.save(minsk);
        cityRepository.save(moscow);
        cityRepository.save(london);
        cityRepository.save(gotham);

        Optional<City> result = cityRepository.findByCityName("Москва");
        assertTrue(result.isPresent());

    }
}