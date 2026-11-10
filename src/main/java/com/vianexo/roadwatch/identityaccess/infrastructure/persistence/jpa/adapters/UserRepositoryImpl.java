package com.vianexo.roadwatch.identityaccess.infrastructure.persistence.jpa.adapters;

import com.vianexo.roadwatch.identityaccess.domain.model.aggregates.User;
import com.vianexo.roadwatch.identityaccess.domain.repositories.UserRepository;
import com.vianexo.roadwatch.identityaccess.infrastructure.persistence.jpa.repositories.UserPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserPersistenceRepository userPersistenceRepository;

    public UserRepositoryImpl(UserPersistenceRepository userPersistenceRepository) {
        this.userPersistenceRepository = userPersistenceRepository;
    }

    @Override
    public User save(User user) {
        return userPersistenceRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userPersistenceRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userPersistenceRepository.findByEmail(email);
    }
}
