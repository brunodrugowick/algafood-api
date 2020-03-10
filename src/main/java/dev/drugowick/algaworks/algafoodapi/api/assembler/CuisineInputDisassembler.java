package dev.drugowick.algaworks.algafoodapi.api.assembler;

import dev.drugowick.algaworks.algafoodapi.api.model.input.CuisineInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CuisineInputDisassembler {

    private ModelMapper modelMapper;

    public CuisineInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cuisine toDomain(CuisineInput cuisineInput) {
        return modelMapper.map(cuisineInput, Cuisine.class);
    }

    public void copyToDomainObject(CuisineInput cuisineInput, Cuisine cuisine) {
        modelMapper.map(cuisineInput, cuisine);
    }
}
