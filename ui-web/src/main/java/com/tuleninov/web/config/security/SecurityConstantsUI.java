package com.tuleninov.web.config.security;

public final class SecurityConstantsUI {

    private SecurityConstantsUI() {
        throw new AssertionError("non-instantiable class");
    }

    public static final String AUTH_TOKEN_PREFIX = "Bearer ";

    public static final String AUTH_CLAIM = "Authorization";
}
