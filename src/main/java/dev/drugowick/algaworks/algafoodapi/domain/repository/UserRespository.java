package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.User;

import java.util.Optional;

public interface UserRespository extends CustomJpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
