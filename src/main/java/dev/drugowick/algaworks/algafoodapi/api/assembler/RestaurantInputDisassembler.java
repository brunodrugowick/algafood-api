package dev.drugowick.algaworks.algafoodapi.api.assembler;

import dev.drugowick.algaworks.algafoodapi.api.model.input.RestaurantInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RestaurantInputDisassembler {

    private ModelMapper modelMapper;

    public RestaurantInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Restaurant toDomain(RestaurantInput restaurantInput) {
        return modelMapper.map(restaurantInput, Restaurant.class);
    }

    public void copyToDomainObject(RestaurantInput restaurantInput, Restaurant restaurant) {
        /**
         * Ok, this line is not trivial to understand.
         * What is happening here is that the Cuisine and Address on the restaurant are managed by JPA, but this
         * operation is only changing the reference of a Cuisine on a Restaurant (or a City on an Address). For this
         * reason we change the Cuisine and Address to a new one to them update its references to a new Cuisine and
         * City from the Address.
         *
         * To be honest I don't like this solution, but I don't have a better one. =(
         */
        restaurant.setCuisine(new Cuisine());

        if (restaurant.getAddress() != null) {
            restaurant.getAddress().setCity(new City());
        }

        modelMapper.map(restaurantInput, restaurant);
    }
}
