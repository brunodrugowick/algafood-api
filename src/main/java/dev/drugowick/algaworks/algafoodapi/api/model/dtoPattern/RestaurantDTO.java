package dev.drugowick.algaworks.algafoodapi.api.model.dtoPattern;

import dev.drugowick.algaworks.algafoodapi.domain.validation.Multiple;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public enum RestaurantDTO {;

    private interface Id { @NotNull Long getId(); }
    private interface Name { @NotBlank String getName(); }
    private interface Delivery { @Multiple(number = 3) BigDecimal getDelivery(); }
    private interface Active { @NotNull Boolean getActive(); }
    private interface Opened { @NotNull Boolean getOpened(); }

    public enum Response {;
        @Getter @Setter @NoArgsConstructor public static class Default implements Id, Name, Delivery, Active, Opened {
            Long id;
            String name;
            BigDecimal delivery;
            Boolean active;
            Boolean opened;
        }
    }

    //private CuisineModel cuisine;
    //private AddressModel address;
}
