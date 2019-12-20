package dev.drugowick.algaworks.algafoodapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "group_")
public class Group {

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

    @ManyToMany
    @JoinTable(name = "group_permission",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions = new ArrayList<>();
}
