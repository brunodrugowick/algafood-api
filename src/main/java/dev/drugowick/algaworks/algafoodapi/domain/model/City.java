package dev.drugowick.algaworks.algafoodapi.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "province_id"}))
public class City {

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
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Province province;

}
