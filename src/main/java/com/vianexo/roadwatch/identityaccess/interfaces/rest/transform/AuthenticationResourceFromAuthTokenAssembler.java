package com.vianexo.roadwatch.identityaccess.interfaces.rest.transform;

import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.AuthToken;
import com.vianexo.roadwatch.identityaccess.interfaces.rest.resources.AuthenticationResource;

public final class AuthenticationResourceFromAuthTokenAssembler {

    private AuthenticationResourceFromAuthTokenAssembler() {
    }

    public static AuthenticationResource toResourceFrom(AuthToken authToken) {
        return new AuthenticationResource(authToken.token(), authToken.expiresAt());
    }
}
