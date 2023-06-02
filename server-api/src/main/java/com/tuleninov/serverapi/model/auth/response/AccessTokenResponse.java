package com.tuleninov.serverapi.model.auth.response;

import com.tuleninov.serverapi.model.user.KnownAuthority;

import java.util.Set;

public record AccessTokenResponse(String accessToken,
                                  String refreshToken,
                                  long expireIn,
                                  Set<KnownAuthority> authorities) {
}
