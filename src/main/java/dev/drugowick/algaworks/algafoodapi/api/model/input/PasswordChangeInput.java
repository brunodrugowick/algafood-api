package dev.drugowick.algaworks.algafoodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordChangeInput {

    @NotBlank
    private String password;

    @NotBlank
    private String newPassword;
}
