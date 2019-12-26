package dev.drugowick.algaworks.algafoodapi.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class Address {

    @Column(name = "address_postalCode")
    private String postalCode;

    @Column(name = "address_address_line_1")
    private String addressLine_1;

    @Column(name = "address_address_line_2")
    private String addressLine_2;

    @Column(name = "address_region")
    private String region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_city_id")
    private City city;
}
