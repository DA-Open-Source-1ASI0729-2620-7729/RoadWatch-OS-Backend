package com.vianexo.roadwatch.identityaccess.application.internal.services;

import com.vianexo.roadwatch.identityaccess.application.outboundservices.hashing.PasswordEncoder;
import com.vianexo.roadwatch.identityaccess.application.outboundservices.tokens.TokenProvider;
import com.vianexo.roadwatch.identityaccess.application.services.AuthenticationService;
import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.AuthToken;
import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.UserStatus;
import com.vianexo.roadwatch.identityaccess.domain.repositories.UserRepository;
import com.vianexo.roadwatch.shared.application.result.UnauthorizedException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthToken authenticate(String email, String password) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));
        if (user.getStatus() != UserStatus.ACTIVE
                || !passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        return tokenProvider.generateToken(user);
    }

    @Override
    public boolean validateToken(String token) {
        return tokenProvider.validateToken(token);
    }
}
