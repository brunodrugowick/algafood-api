package dev.drugowick.algaworks.algafoodapi.domain.model;

import dev.drugowick.algaworks.algafoodapi.domain.validation.DeliveryFee;
import dev.drugowick.algaworks.algafoodapi.domain.validation.Multiple;
import dev.drugowick.algaworks.algafoodapi.domain.validation.ValidationGroups;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * I'm choosing to keep the domain objects validation prone. This is a project decision considering there may e some
 * for of integration that's not via the API we are providing ourselves.
 *
 * For example, if this application has a CLI or if another module or app access the domain directly (without using the
 * controllers) validation may come in handy.
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

	@NotNull(groups = ValidationGroups.RestaurantId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * @Column is included to exemplify its use, but it's not
	 * recommended here since the database will be generated
	 * via script and this is not a validation for the object,
	 * only a constraint on the database.
	 */

	@NotBlank
	@Column(nullable = false)
	private String name;

	@Multiple(number = 3)
	@DeliveryFee
	@Column(nullable = false)
	private BigDecimal deliveryFee;

	@NotNull
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationGroups.CuisineId.class)
	@ManyToOne
	@JoinColumn(name = "cuisine_id", nullable = false)
	private Cuisine cuisine;

	@Embedded
	private Address address;

	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime", updatable = false)
	private OffsetDateTime createdDate;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime updatedDate;

	@Column(nullable = false)
	private Boolean active = Boolean.TRUE;

	@ManyToMany
	@JoinTable(name = "restaurant_payment_method",
			joinColumns = @JoinColumn(name = "restaurant_id"),
			inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
	private List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();

	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();

	public void activate() {
		setActive(true);
	}

	public void deactivate() {
		setActive(false);
	}

}
