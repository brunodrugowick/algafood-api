package dev.drugowick.algaworks.algafoodapi.api.assembler;

import dev.drugowick.algaworks.algafoodapi.api.model.input.RestaurantInput;
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
         * What is happening here is that the Cuisine on the restaurant is managed by JPA, but this operation is only
         * changing the reference of a Cuisine on a Restaurant. For this reason we change the Cuisine to a new one to
         * them update its reference to a new Cuisine.
         *
         * To be honest I don't like this solution, but I don't have a better one. =(
         */
        restaurant.setCuisine(new Cuisine());

        modelMapper.map(restaurantInput, restaurant);
    }
}
