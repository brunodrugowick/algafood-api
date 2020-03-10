package dev.drugowick.algaworks.algafoodapi.api.assembler;

import dev.drugowick.algaworks.algafoodapi.api.model.CuisineModel;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CuisineModelAssembler {

    private ModelMapper modelMapper;

    public CuisineModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CuisineModel toModel(Cuisine cuisine) {
        return modelMapper.map(cuisine, CuisineModel.class);
    }

    public List<CuisineModel> toCollectionModel(List<Cuisine> cuisines) {
        return cuisines.stream()
                .map(cuisine -> toModel(cuisine))
                .collect(Collectors.toList());
    }
}
