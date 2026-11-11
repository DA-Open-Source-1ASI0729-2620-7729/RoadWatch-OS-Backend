package com.vianexo.roadwatch.identityaccess.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequestResource(
        @Email @NotBlank String email,
        @NotBlank String password) {
}
