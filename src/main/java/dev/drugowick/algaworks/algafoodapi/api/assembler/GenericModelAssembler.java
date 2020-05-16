package dev.drugowick.algaworks.algafoodapi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A generic Model Assembler that uses ModelMapper.
 *
 * @param <T> Model type to assemble from.
 * @param <S> Domain type to assemble to.
 */
@Component
public class GenericModelAssembler<T, S> {

    private ModelMapper modelMapper;

    public GenericModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public S toModel(T domainObject, Class<S> type) {
        return modelMapper.map(domainObject, type);
    }

    public List<S> toCollectionModel(Collection<T> objects, Class<S> type) {
        return objects.stream()
                .map(object -> toModel(object, type))
                .collect(Collectors.toList());
    }
}
