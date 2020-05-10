package dev.drugowick.algaworks.algafoodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNoPasswordInput {

    private Long id;
    private String name;
    private String email;
}
