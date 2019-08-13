package by.belnarto.telegrambot.controller;

import by.belnarto.telegrambot.model.City;
import by.belnarto.telegrambot.model.CityDto;
import by.belnarto.telegrambot.service.CityService;
import by.belnarto.telegrambot.service.mapper.CityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CityController {

    private CityService cityService;
    private CityMapper cityMapper;

    public CityController(final CityService cityService,
                          final CityMapper cityMapper) {
        this.cityService = cityService;
        this.cityMapper = cityMapper;
    }

    @GetMapping(value = "/cities")
    public ResponseEntity<List<CityDto>> findAllCities(
            @RequestParam Map<String, String> allParams) {

        if (allParams.isEmpty()) {
            List<CityDto> cities = cityService.findAll().stream()
                    .map(city -> cityMapper.toDto(city))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(cities, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/cities")
    public ResponseEntity<List<CityDto>> createCity(
            @Valid @RequestBody CityDto cityDto) {
        try {
            City city = cityMapper.toEntity(cityDto);
            city = cityService.save(city);
            List<CityDto> result = new ArrayList<>();
            result.add(cityMapper.toDto(city));
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cities/{cityId}")
    public ResponseEntity<List<CityDto>> findCityById(
            @PathVariable("cityId") Long cityId) {

        Optional<City> cityOptional = cityService.findById(cityId);
        if (cityOptional.isPresent()) {
            List<CityDto> result = new ArrayList<>();
            result.add(cityMapper.toDto(cityOptional.get()));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/cities/{cityId}")
    public ResponseEntity<List<CityDto>> updateCity(@PathVariable("cityId") Long cityId,
                                                    @Valid @RequestBody CityDto cityDto) {

        Optional<City> cityOptional = cityService.findById(cityId);

        if (!cityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!cityId.equals(cityDto.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            List<CityDto> result = new ArrayList<>();
            City city = cityService.update(cityMapper.toEntity(cityDto));
            result.add(cityMapper.toDto(city));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/cities/{cityId}")
    public ResponseEntity deleteCity(@PathVariable("cityId") Long cityId) {

        Optional<City> cityOptional = cityService.findById(cityId);

        if (cityOptional.isPresent()) {
            try {
                cityService.delete(cityOptional.get());
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
