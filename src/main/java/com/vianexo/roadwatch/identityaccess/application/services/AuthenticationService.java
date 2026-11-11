package com.vianexo.roadwatch.identityaccess.application.services;

import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.AuthToken;

public interface AuthenticationService {

    AuthToken authenticate(String email, String password);

    boolean validateToken(String token);
}
