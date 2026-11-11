package com.vianexo.roadwatch.identityaccess.domain.model.valueobjects;

import java.time.LocalDateTime;

public record AuthToken(String token, LocalDateTime expiresAt) {
}
