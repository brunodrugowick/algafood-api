package dev.drugowick.algaworks.algafoodapi.config;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Confiures the ObjectMerger objects to instantiate as Beans.
 * <p>
 * These Beans will be used on Controllers for object transformation from Map<String, Object> to <T> object.
 * This is especially useful for PATCH HTTP requests.
 */
@Configuration
public class ObjectMergerConfig {

    @Bean
    public ObjectMerger<Restaurant> restaurantObjectMerger() {
        return new ObjectMerger<>(Restaurant.class);
    }

    @Bean
    public ObjectMerger<Cuisine> cuisineObjectMerger() {
        return new ObjectMerger<>(Cuisine.class);
    }

    @Bean
    public ObjectMerger<City> cityObjectMerger() {
        return new ObjectMerger<>(City.class);
    }

    @Bean
    public ObjectMerger<Province> provinceObjectMerger() {
        return new ObjectMerger<>(Province.class);
    }
}
