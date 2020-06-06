package dev.drugowick.algaworks.algafoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ProductPhoto {

    @EqualsAndHashCode.Include
    @Id
    private Long productId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;

    private String fileName;
    private String description;
    private String contentType;
    private Long size;

    public Long getRestaurantId() {
        return getProduct() == null ? null : this.product.getRestaurant().getId();
    }
}
