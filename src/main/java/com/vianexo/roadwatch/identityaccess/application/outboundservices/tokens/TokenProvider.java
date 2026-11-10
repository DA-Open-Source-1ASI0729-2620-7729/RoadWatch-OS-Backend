package com.vianexo.roadwatch.identityaccess.application.outboundservices.tokens;

import com.vianexo.roadwatch.identityaccess.domain.model.aggregates.User;
import com.vianexo.roadwatch.identityaccess.domain.model.valueobjects.AuthToken;

public interface TokenProvider {

    AuthToken generateToken(User user);

    boolean validateToken(String token);

    Long getUserId(String token);
}
