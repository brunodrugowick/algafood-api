package dev.drugowick.algaworks.algafoodapi.config;

import dev.drugowick.algaworks.algafoodapi.api.model.AddressModel;
import dev.drugowick.algaworks.algafoodapi.domain.model.Address;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Address, AddressModel> addressAddressModelTypeMap = modelMapper.createTypeMap(Address.class, AddressModel.class);
        addressAddressModelTypeMap.<String>addMapping((source) -> source.getCity().getProvince().getName(),
                (destination, value) -> destination.getCity().setProvince(value));
        return modelMapper;
    }
}
