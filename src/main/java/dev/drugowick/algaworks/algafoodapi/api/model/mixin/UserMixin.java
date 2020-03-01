package dev.drugowick.algaworks.algafoodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;

import java.util.ArrayList;
import java.util.List;

public abstract class UserMixin {

    @JsonIgnore
    private List<Permission> groups = new ArrayList<>();
}
