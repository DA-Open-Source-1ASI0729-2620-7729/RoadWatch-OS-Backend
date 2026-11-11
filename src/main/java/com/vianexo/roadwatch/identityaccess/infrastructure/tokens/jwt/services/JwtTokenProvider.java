package com.vianexo.roadwatch.identityaccess.infrastructure.tokens.jwt.services;

import com.vianexo.roadwatch.identityaccess.application.outboundservices.tokens.TokenProvider;
import com.vianexo.roadwatch.identityaccess.domain.model.aggregates.User;
import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.AuthToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final SecretKey signingKey;
    private final long expirationHours;

    public JwtTokenProvider(
            @Value("${JWT_SECRET}") String secret,
            @Value("${JWT_EXPIRATION_HOURS:24}") long expirationHours) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationHours = expirationHours;
    }

    @Override
    public AuthToken generateToken(User user) {
        var expiresAt = LocalDateTime.now().plusHours(expirationHours);
        var token = Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .expiration(Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(signingKey)
                .compact();
        return new AuthToken(token, expiresAt);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    @Override
    public Long getUserId(String token) {
        return Long.valueOf(Jwts.parser().verifyWith(signingKey).build()
                .parseSignedClaims(token).getPayload().getSubject());
    }
}
