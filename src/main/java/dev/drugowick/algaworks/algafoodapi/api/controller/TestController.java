package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * This is a only a test class for new endpoints (to be added at some point).
 * <p>
 * No HTTP codes are considered and error treatment is minimal.
 */
@RestController
@RequestMapping("/beta")
public class TestController {

    private CuisineRepository cuisineRepository;
    private RestaurantRepository restaurantRepository;
    private CityRepository cityRepository;

    public TestController(CuisineRepository cuisineRepository, RestaurantRepository restaurantRepository, CityRepository cityRepository) {
        this.cuisineRepository = cuisineRepository;
        this.restaurantRepository = restaurantRepository;
        this.cityRepository = cityRepository;
    }

    @GetMapping("/cuisines/by-name")
    public List<Cuisine> cuisinesByName(@RequestParam("name") String namePartial) {
        // TODO verify if namePartial is empty, otherwise this'll be used as findAll().
        return cuisineRepository.findAllByNameContaining(namePartial);
    }

    @GetMapping("/restaurants/by-cuisine")
    public List<Restaurant> restaurantsByCuisine(@RequestParam("cuisine") String cuisine) {
        // TODO verify if cuisine is empty, otherwise this'll be used as findAll().
        return restaurantRepository.findAllByCuisineNameContaining(cuisine);
    }

    @GetMapping("/restaurants/by-deliveryFee")
    public List<Restaurant> restaurantsByDeliveryFee(@RequestParam("startingFee") BigDecimal startingFee,
                                                     @RequestParam("endingFee") BigDecimal endingFee) {
        return restaurantRepository.findAllByDeliveryFeeBetween(startingFee, endingFee);
    }

    @GetMapping("/cities/by-name-and-provinceAbbreviation")
    public City citiesByNameAndProvinceAbbreviation(@RequestParam("cityName") String cityName,
                                                    @RequestParam("provinceAbbreviation") String provinceAbbreviation) {
        // TODO verify if params are empty, otherwise this'll be used as findAll().
        return cityRepository.findFirstByNameContainingAndProvinceAbbreviationContaining(cityName, provinceAbbreviation);
    }

    @GetMapping("/cities/by-name-starting-with")
    public List<City> citiesByNameStartingWith(@RequestParam("cityStart") String cityStart) {
        // TODO verify if params are empty, otherwise this'll be used as findAll().
        return cityRepository.findAllByNameStartingWith(cityStart);
    }
}
