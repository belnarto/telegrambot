package by.belnarto.telegrambot.repository;

import by.belnarto.telegrambot.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
