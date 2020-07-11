package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Managers")
    private String name;
}
