package dev.drugowick.algaworks.algafoodapi.api.model.dtoPattern;

import dev.drugowick.algaworks.algafoodapi.domain.validation.Multiple;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public enum RestaurantDTO {;

    private interface Id { @NotNull Long getId(); }
    private interface Name { @NotBlank String getName(); }
    private interface Delivery { @Multiple(number = 3) BigDecimal getDelivery(); }
    private interface DeliveryFee { @Multiple(number = 3) BigDecimal getDeliveryFee(); }
    private interface Cuisine { @NotNull CuisineDTO.Response.Summary getCuisine(); }
    private interface CuisineId { @NotNull CuisineDTO.Request.IdOnly getCuisine(); }
    private interface Active { @NotNull Boolean getActive(); }
    private interface Opened { @NotNull Boolean getOpened(); }
    private interface Address { @NotNull @Valid AddressDTO.Response.Default getAddress(); }
    private interface AddressRequest { @NotNull @Valid AddressDTO.Request.Default getAddress(); }

    public enum Response {;
        @Getter @Setter @NoArgsConstructor public static class Default
                implements Id, Name, Delivery, Cuisine, Active, Opened {
            Long id;
            String name;
            BigDecimal delivery;
            CuisineDTO.Response.Summary cuisine;
            Boolean active;
            Boolean opened;
            AddressDTO.Response.Default address;
        }

        @Getter @Setter @NoArgsConstructor public static class Summary
                implements Id, Name {
            Long id;
            String name;
            CuisineDTO.Response.Summary cuisine;
        }
    }

    public enum Request {;
        @Getter @Setter @NoArgsConstructor public static class Default
                implements Name, DeliveryFee, CuisineId, AddressRequest {
            String name;
            BigDecimal deliveryFee;
            CuisineDTO.Request.IdOnly cuisine;
            AddressDTO.Request.Default address;
        }
    }
}
