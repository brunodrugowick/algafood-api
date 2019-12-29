package dev.drugowick.algaworks.algafoodapi.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class Address {

    @Column(name = "address_postal_code", nullable = false)
    private String postalCode;

    @Column(name = "address_address_line_1", nullable = false)
    private String addressLine_1;

    @Column(name = "address_address_line_2")
    private String addressLine_2;

    @Column(name = "address_region", nullable = false)
    private String region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id")
    private City city;
}
