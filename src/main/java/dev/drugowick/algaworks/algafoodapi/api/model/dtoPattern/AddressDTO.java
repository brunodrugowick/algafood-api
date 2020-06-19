package dev.drugowick.algaworks.algafoodapi.api.model.dtoPattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public enum AddressDTO {;

    private interface PostalCode { String getPostalCode(); }
    private interface AddressLine_1 { String getAddressLine_1(); }
    private interface AddressLine_2 { String getAddressLine_2(); }
    private interface Region { String getRegion(); }
    private interface City { CityDTO.Default getCity(); }

    public enum CityDTO {;

        private interface Name { String getName(); }
        private interface Province { String getProvince(); }

        @Getter @Setter @NoArgsConstructor
        public static class Default implements Name, Province {
            String name;
            String province;
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

}
