package dev.drugowick.algaworks.algafoodapi.domain.model;

import dev.drugowick.algaworks.algafoodapi.api.validation.ValidationGroups;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "abbreviation"}))
public class Province {

	@NotNull(groups = ValidationGroups.ProvinceId.class)
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

	@NotBlank
	@Column(nullable = false)
	private String abbreviation;

}
