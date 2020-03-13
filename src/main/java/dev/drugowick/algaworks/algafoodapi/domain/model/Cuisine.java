package dev.drugowick.algaworks.algafoodapi.domain.model;

import dev.drugowick.algaworks.algafoodapi.domain.validation.ValidationGroups;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Cuisine {

	@NotNull(groups = ValidationGroups.CuisineId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "cuisine")
	List<Restaurant> restaurants;

	/**
	 * @Column is included to exemplify its use, but it's not
	 * recommended here since the database will be generated
	 * via script and this is not a validation for the object,
	 * only a constraint on the database.
	 */
	@NotBlank
	@Column(nullable = false)
	private String name;

}
