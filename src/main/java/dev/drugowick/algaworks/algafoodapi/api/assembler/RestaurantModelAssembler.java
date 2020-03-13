package dev.drugowick.algaworks.algafoodapi.api.assembler;

import dev.drugowick.algaworks.algafoodapi.api.model.RestaurantModel;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantModelAssembler {

    private ModelMapper modelMapper;

    public RestaurantModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RestaurantModel toModel(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantModel.class);
    }

    public List<RestaurantModel> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(restaurant -> toModel(restaurant))
                .collect(Collectors.toList());
    }
}
