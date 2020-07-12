package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageModel<T> {

    private List<T> content;

    @ApiModelProperty(example = "100", value = "Total number of elements")
    private long totalElements;

    @ApiModelProperty(example = "10", value = "Total number of pages considering the current page size")
    private int totalPages;

    @ApiModelProperty(example = "0", value = "Current page number (starts at 0)")
    private int page;

    @ApiModelProperty(example = "10", value = "Page size. Total number of elements per page")
    private int size;
}
