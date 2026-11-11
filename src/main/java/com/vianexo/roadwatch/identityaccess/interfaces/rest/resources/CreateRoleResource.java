package com.vianexo.roadwatch.identityaccess.interfaces.rest.resources;

import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.RoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRoleResource(@NotNull RoleType name, @NotBlank String description) {
}
