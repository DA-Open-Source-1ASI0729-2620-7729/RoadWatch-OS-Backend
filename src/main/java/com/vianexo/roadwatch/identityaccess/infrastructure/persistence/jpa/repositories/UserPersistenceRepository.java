package com.vianexo.roadwatch.identityaccess.infrastructure.persistence.jpa.repositories;

import com.vianexo.roadwatch.identityaccess.domain.model.aggregates.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPersistenceRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findById(Long id);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);
}
