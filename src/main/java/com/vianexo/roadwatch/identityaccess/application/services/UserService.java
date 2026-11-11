package com.vianexo.roadwatch.identityaccess.application.services;

import com.vianexo.roadwatch.identityaccess.domain.model.aggregates.User;
import com.vianexo.roadwatch.identityaccess.domain.model.entities.Role;
import com.vianexo.roadwatch.identityaccess.application.outboundservices.hashing.PasswordEncoder;
import com.vianexo.roadwatch.identityaccess.domain.repositories.RoleRepository;
import com.vianexo.roadwatch.identityaccess.domain.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        user.changePassword(passwordEncoder.encode(user.getPasswordHash()));
        user.activate();
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: %s".formatted(id)));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found: %s".formatted(email)));
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deactivateUser(Long id) {
        var user = findById(id);
        user.deactivate();
        userRepository.save(user);
    }

    public User assignSubscriptionAccount(Long userId, Long accountId) {
        var user = findById(userId);
        user.assignSubscriptionAccount(accountId);
        return userRepository.save(user);
    }

    @Transactional
    public void assignRole(Long userId, Long roleId) {
        var user = findById(userId);
        var role = findRole(roleId);
        user.assignRole(role);
        userRepository.save(user);
    }

    @Transactional
    public void removeRole(Long userId, Long roleId) {
        var user = findById(userId);
        var role = findRole(roleId);
        user.removeRole(role);
        userRepository.save(user);
    }

    private Role findRole(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: %s".formatted(id)));
    }
}
