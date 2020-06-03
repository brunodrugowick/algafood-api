package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageModel<T> {

    private List<T> content;

    private long totalElements;
    private int totalPages;
    private int page;
    private int size;
}
