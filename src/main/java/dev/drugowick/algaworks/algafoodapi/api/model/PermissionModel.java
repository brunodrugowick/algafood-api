package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "READ")
    private String name;

    @ApiModelProperty(example = "Permission to read records")
    private String description;

}
