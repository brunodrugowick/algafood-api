package dev.drugowick.algaworks.algafoodapi.api.controller.openapi.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel("Pageable")
@Setter
@Getter
public class PageableModelOpenApi {

    @ApiModelProperty(example = "0", value = "Page number (starts at 0)")
    private int page;

    @ApiModelProperty(example = "10", value = "Amount of elements per page")
    private int size;

    @ApiModelProperty(example = "name,asc", value = "Property to order based on (may include direction)")
    private List<String> sort;
}
