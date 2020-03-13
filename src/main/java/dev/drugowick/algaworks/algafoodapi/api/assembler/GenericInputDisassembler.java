package dev.drugowick.algaworks.algafoodapi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * A generic Input disassembler that uses Model Mapper.
 *
 * @param <T> Input to disassemble from.
 * @param <S> Domain to disassemble to.
 */
@Component
public class GenericInputDisassembler<T, S> {

    private ModelMapper modelMapper;

    public GenericInputDisassembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public S toDomain(T originInput, Class<S> type) {
        return modelMapper.map(originInput, type);
    }

    public void copyToDomainObject(T originInput, Object destination) {
        modelMapper.map(originInput, destination);
    }
}
