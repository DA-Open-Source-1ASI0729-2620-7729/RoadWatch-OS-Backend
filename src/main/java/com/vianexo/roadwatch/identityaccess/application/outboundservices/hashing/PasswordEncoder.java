package com.vianexo.roadwatch.identityaccess.application.outboundservices.hashing;

public interface PasswordEncoder {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);
}
