package com.vianexo.roadwatch.identityaccess.domain.repositories;

import com.vianexo.roadwatch.identityaccess.domain.model.entities.Role;

import java.util.Optional;

public interface RoleRepository {

    Role save(Role role);

    Optional<Role> findById(Long id);
}
