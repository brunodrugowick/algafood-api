package dev.drugowick.algaworks.algafoodapi.api.model.dtoPattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public enum AddressDTO {;

    private interface PostalCode { @NotBlank String getPostalCode(); }
    private interface AddressLine_1 { @NotBlank String getAddressLine_1(); }
    private interface AddressLine_2 { @NotBlank String getAddressLine_2(); }
    private interface Region { @NotBlank String getRegion(); }
    private interface City { @NotNull @Valid CityDTO.Default getCity(); }
    private interface CityId { @NotNull @Valid CityDTO.CityId getCity(); }

    public enum CityDTO {;

        private interface Id { @NotNull Long getId(); }
        private interface Name { @NotBlank String getName(); }
        private interface Province { @NotBlank String getProvince(); }

        @Getter @Setter @NoArgsConstructor
        public static class Default implements Name, Province {
            String name;
            String province;
        }

        @Getter @Setter @NoArgsConstructor
        public static class CityId implements Id {
            Long id;
        }

    }

    public enum Response {;
        @Getter @Setter @NoArgsConstructor
        public static class Default implements PostalCode, AddressLine_1, AddressLine_2, Region, City {
            String postalCode;
            String addressLine_1;
            String addressLine_2;
            String region;
            CityDTO.Default city;
        }

    }


    public enum Request {;
        @Getter @Setter @NoArgsConstructor
        public static class Default implements PostalCode, AddressLine_1, AddressLine_2, Region, CityId {
            String postalCode;
            String addressLine_1;
            String addressLine_2;
            String region;
            CityDTO.CityId city;
        }
    }

}
