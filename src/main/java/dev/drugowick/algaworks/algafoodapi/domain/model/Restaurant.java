package dev.drugowick.algaworks.algafoodapi.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {
	
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
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private BigDecimal deliveryFee;
	
	@ManyToOne
	private Cuisine cuisine;

}