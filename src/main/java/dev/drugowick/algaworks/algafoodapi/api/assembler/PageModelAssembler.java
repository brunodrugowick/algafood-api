package dev.drugowick.algaworks.algafoodapi.api.assembler;

import dev.drugowick.algaworks.algafoodapi.api.model.PageModel;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class PageModelAssembler<T, S> {

    private ModelMapper modelMapper;

    public PageModelAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PageModel<S> toCollectionModelPage(Page page, Class<S> type) {
        PageModel<S> pageModel = new PageModel<>();
        Collection<T> domainElementsList = (Collection<T>) page.getContent();
        pageModel.setContent(
                domainElementsList.stream()
                        .map(object -> modelMapper.map(object, type))
                        .collect(Collectors.toList()));
        pageModel.setPage(page.getNumber());
        pageModel.setSize(page.getSize());
        pageModel.setTotalElements(page.getTotalElements());
        pageModel.setTotalPages(page.getTotalPages());

        return pageModel;
    }
}
