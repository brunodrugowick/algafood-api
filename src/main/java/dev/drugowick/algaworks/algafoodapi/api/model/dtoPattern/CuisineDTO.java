package dev.drugowick.algaworks.algafoodapi.api.model.dtoPattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public enum CuisineDTO {;

    private interface Id { @NotNull Long getId(); }
    private interface Name { @NotBlank String getName(); }

    public enum Response {;
        @Getter @Setter @NoArgsConstructor public static class Summary implements Id, Name {
            Long id;
            String name;
        }

    }

    public enum Request {;
        @Getter @Setter @NoArgsConstructor public static class IdOnly implements Id {
            Long id;
        }
    }

}
