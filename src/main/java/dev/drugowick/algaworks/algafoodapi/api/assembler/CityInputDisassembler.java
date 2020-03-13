package dev.drugowick.algaworks.algafoodapi.api.assembler;

import dev.drugowick.algaworks.algafoodapi.api.model.input.CityInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CityInputDisassembler {

    private ModelMapper modelMapper;

    public CityInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public City toDomain(CityInput cityInput) {
        return modelMapper.map(cityInput, City.class);
    }

    public void copyToDomainObject(CityInput cityInput, City city) {
        /**
         * Ok, this line is not trivial to understand.
         * What is happening here is that the Province on the City is managed by JPA, but this operation is only
         * changing the reference of a Province on a City. For this reason we change the Province to a new one to
         * them update its reference to a new Province.
         *
         * To be honest I don't like this solution, but I don't have a better one. =(
         */
        city.setProvince(new Province());

        modelMapper.map(cityInput, city);
    }
}
