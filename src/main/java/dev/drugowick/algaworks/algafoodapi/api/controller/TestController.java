package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.*;
import dev.drugowick.algaworks.algafoodapi.domain.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    private PermissionRepository permissionRepository;
    private PaymentMethodRepository paymentMethodRepository;
    private OrderRepository orderRepository;

    public TestController(CuisineRepository cuisineRepository, RestaurantRepository restaurantRepository, CityRepository cityRepository, PermissionRepository permissionRepository, PaymentMethodRepository paymentMethodRepository, OrderRepository orderRepository) {
        this.cuisineRepository = cuisineRepository;
        this.restaurantRepository = restaurantRepository;
        this.cityRepository = cityRepository;
        this.permissionRepository = permissionRepository;
        this.paymentMethodRepository = paymentMethodRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/cuisines/by-name")
    public List<Cuisine> cuisinesByName(@RequestParam("name") String namePartial) {
        // TODO verify if namePartial is empty, otherwise this'll be used as findAll().
        return cuisineRepository.byNameLike(namePartial);
    }

    @GetMapping("/restaurants/by-cuisine")
    public List<Restaurant> restaurantsByCuisine(@RequestParam("cuisine") String cuisine) {
        // TODO verify if cuisine is empty, otherwise this'll be used as findAll().
        return restaurantRepository.byCuisineLike(cuisine);
    }

    @GetMapping("/restaurants/by-deliveryFee")
    public List<Restaurant> restaurantsByDeliveryFee(@RequestParam("startingFee") BigDecimal startingFee,
                                                     @RequestParam("endingFee") BigDecimal endingFee) {
        return restaurantRepository.byDeliveryFee(startingFee, endingFee);
    }

    @GetMapping("/cities/by-name-and-provinceAbbreviation")
    public Optional<City> citiesByNameAndProvinceAbbreviation(@RequestParam("cityName") String cityName,
                                                              @RequestParam("provinceAbbreviation") String provinceAbbreviation) {
        // TODO verify if params are empty, otherwise this'll be used as findAll().
        return cityRepository.findFirstByNameContainingAndProvinceAbbreviationContaining(cityName, provinceAbbreviation);
    }

    @GetMapping("/cities/by-name-starting-with")
    public List<City> citiesByNameStartingWith(@RequestParam("cityStart") String cityStart) {
        // TODO verify if params are empty, otherwise this'll be used as findAll().
        return cityRepository.byNameStarting(cityStart);
    }

    @GetMapping("/permissions/exists-by-name")
    public boolean permissionsExistsyName(@RequestParam("permission") String name) {
        return permissionRepository.existsByName(name);
    }

    @GetMapping("/cities/count-by-provinceAbbreviation")
    public int countCitiesByProvince(@RequestParam("province") String provinceAbbreviation) {
        return cityRepository.countByProvince(provinceAbbreviation);
    }

    @GetMapping("/restaurants/find")
    public List<Restaurant> findRestaurants(@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "startingFee", required = false) BigDecimal startingFee,
                                            @RequestParam(value = "endingFee", required = false) BigDecimal endingFee,
                                            @RequestParam(value = "cuisine", required = false) String cuisine) {
        // In this case there's no need to test for null params, since I'm going to do that on the RepositoryImpl'
        return restaurantRepository.findByAll(name, startingFee, endingFee, cuisine);
    }

    @GetMapping("/restaurants/find-criteria")
    public List<Restaurant> findRestaurantsCriteriaApi(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "startingFee", required = false) BigDecimal startingFee,
                                                       @RequestParam(value = "endingFee", required = false) BigDecimal endingFee,
                                                       @RequestParam(value = "cuisine", required = false) String cuisine) {
        // In this case there's no need to test for null params, since I'm going to do that on the RepositoryImpl'
        return restaurantRepository.findByAllCriteriaApi(name, startingFee, endingFee, cuisine);
    }

    @GetMapping("/restaurants/find-querydsl")
    public List<Restaurant> findRestaurantsQueryDsl(@RequestParam(value = "name", required = false) String name,
                                                    @RequestParam(value = "startingFee", required = false) BigDecimal startingFee,
                                                    @RequestParam(value = "endingFee", required = false) BigDecimal endingFee,
                                                    @RequestParam(value = "cuisine", required = false) String cuisine) {
        // In this case there's no need to test for null params, since I'm going to do that on the RepositoryImpl'
        return restaurantRepository.findByAllQueryDsl(name, startingFee, endingFee, cuisine);
    }

    @GetMapping("/restaurants/free-delivery-spec")
    public List<Restaurant> freeDeliveryRestaurants(String name) {

        return restaurantRepository.findFreeDelivery(name);
    }

    @GetMapping("/restaurants/first")
    public Optional<Restaurant> restaurantFirst() {
        return restaurantRepository.findFirst();
    }

    @GetMapping("/payment-method/first")
    public Optional<PaymentMethod> paymentMethodFirst() {
        return paymentMethodRepository.findFirst();
    }

    /**
     * TODO Before implementing I gotta solve the N+1 problem for this entity
     *
     * @return
     */
    @GetMapping("/orders")
    public List<Order> list() {
        return orderRepository.findAll();
    }

    /**
     * TODO Before implementing I gotta solve the N+1 problem for this entity
     *
     * @return
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> get(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * POST to test the class-level annotation I created
     * at {@link dev.drugowick.algaworks.algafoodapi.domain.validation.IfFreeDeliverySubtotalEqualsTotal}
     */
    @PostMapping("/orders")
    public ResponseEntity<?> saveOrder(@RequestBody @Valid Order order) {
        // Temporary. Client should not send an ID when posting. See #2.
        if (order.getId() != null) {
            throw new GenericBusinessException("You should not send an ID when saving or updating an entity.");
        }

        order = orderRepository.save(order);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(order);
    }

}
