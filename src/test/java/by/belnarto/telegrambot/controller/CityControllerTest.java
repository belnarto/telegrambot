package by.belnarto.telegrambot.controller;

import by.belnarto.telegrambot.model.City;
import by.belnarto.telegrambot.model.CityDto;
import by.belnarto.telegrambot.service.CityService;
import by.belnarto.telegrambot.service.mapper.CityMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CityService cityService;

    @MockBean
    private CityMapper cityMapper;

    @Test
    public void findAllCities() throws Exception {
        City city1 = new City(1L, "Test City1", "Gotham is under batman protection");
        City city2 = new City(2L, "Test City2", "Gotham is under batman protection");
        given(cityService.findAll()).willReturn(Arrays.asList(city1, city2));
        given(cityMapper.toDto(city1)).willReturn(new CityDto(1L, "Test City1", "Gotham is under batman protection"));
        given(cityMapper.toDto(city2)).willReturn(new CityDto(2L, "Test City2", "Gotham is under batman protection"));
        mvc.perform(get("/api/v1/cities")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cityName", is(city1.getCityName())));
    }

    @Test
    public void findCityById() throws Exception {
        City city = new City(5L, "Test City", "Gotham is under batman protection");
        given(cityService.findById(5L)).willReturn(Optional.of(city));
        given(cityMapper.toDto(city)).willReturn(new CityDto(5L, "Test City", "Gotham is under batman protection"));
        mvc.perform(get("/api/v1/cities/5")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].cityName", is(city.getCityName())));
    }

    @Test
    public void createCity() throws Exception {
        CityDto cityDto = new CityDto(0L, "Azgard", "Azgard is fallen.");
        CityDto cityDtoAfterSaving = new CityDto(5L, "Azgard", "Azgard is fallen.");
        City city = new City(0L, "Azgard", "Azgard is fallen.");
        City cityAfterSaving = new City(5L, "Azgard", "Azgard is fallen.");
        given(cityMapper.toEntity(cityDto)).willReturn(city);
        given(cityService.save(city)).willReturn(cityAfterSaving);
        given(cityMapper.toDto(cityAfterSaving)).willReturn(cityDtoAfterSaving);

        mvc.perform(post("/api/v1/cities")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(cityDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(cityAfterSaving.getId().intValue())));

    }

    @Test
    public void updateCity() throws Exception {
        CityDto cityDto = new CityDto(4L, "Gotham", "But the Joker is one step ahead.");
        City city = new City(4L, "Gotham", "Gotham is under batman protection.");
        City cityAfterSaving = new City(4L, "Gotham", "But the Joker is one step ahead.");
        given(cityService.findById(4L)).willReturn(Optional.of(city));
        given(cityMapper.toEntity(cityDto)).willReturn(city);
        given(cityService.update(city)).willReturn(cityAfterSaving);
        given(cityMapper.toDto(cityAfterSaving)).willReturn(cityDto);

        mvc.perform(put("/api/v1/cities/4")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(cityDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].cityInfo", is(cityAfterSaving.getCityInfo())));

    }

    @Test
    public void deleteCity() throws Exception {
        City city = new City(2L, "Москва", "Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))");
        given(cityService.findById(2L)).willReturn(Optional.of(city));
        mvc.perform(delete("/api/v1/cities/2")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }
}