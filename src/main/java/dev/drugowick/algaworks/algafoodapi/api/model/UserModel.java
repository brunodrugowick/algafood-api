package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Rob Dantas")
    private String name;

    @ApiModelProperty(example = "antas@email.com")
    private String email;
}
