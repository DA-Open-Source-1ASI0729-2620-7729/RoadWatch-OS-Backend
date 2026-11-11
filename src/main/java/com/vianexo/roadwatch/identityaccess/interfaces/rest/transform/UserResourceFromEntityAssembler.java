package com.vianexo.roadwatch.identityaccess.interfaces.rest.transform;

import com.vianexo.roadwatch.identityaccess.domain.model.aggregates.User;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.resources.UserResource;

import java.util.stream.Collectors;

public final class UserResourceFromEntityAssembler {

    private UserResourceFromEntityAssembler() {
    }

    public static UserResource toResourceFrom(User user) {
        return new UserResource(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getStatus(),
                user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()),
                user.getCreatedAt().toInstant(),
                user.getUpdatedAt().toInstant());
    }
}
