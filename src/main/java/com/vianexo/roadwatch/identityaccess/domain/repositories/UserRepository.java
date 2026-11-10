package com.vianexo.roadwatch.identityaccess.domain.repositories;

import com.vianexo.roadwatch.identityaccess.domain.model.aggregates.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
