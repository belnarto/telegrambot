package by.belnarto.telegrambot.service.mapper;

import by.belnarto.telegrambot.model.City;
import by.belnarto.telegrambot.model.CityDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityMapperTest {

    @Autowired
    private CityMapper cityMapper;

    @Test
    public void toEntity() {
        CityDto cityDto = new CityDto(1L, "Минск", "Город-герой.");
        City city = cityMapper.toEntity(cityDto);
        assertTrue(cityDto.getId().equals(city.getId()) &&
                cityDto.getCityName().equals(city.getCityName()) &&
                cityDto.getCityInfo().equals(city.getCityInfo()));
    }

    @Test
    public void toDto() {
        City city = new City(1L, "Минск", "Город-герой.");
        CityDto cityDto = cityMapper.toDto(city);
        assertTrue(cityDto.getId().equals(city.getId()) &&
                cityDto.getCityName().equals(city.getCityName()) &&
                cityDto.getCityInfo().equals(city.getCityInfo()));
    }
}