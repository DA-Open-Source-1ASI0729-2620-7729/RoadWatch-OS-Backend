package com.vianexo.roadwatch.identityaccess.interfaces.rest.resources;

import java.time.LocalDateTime;

public record AuthenticationResource(String token, LocalDateTime expiresAt) {
}
