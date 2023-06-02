package com.tuleninov.web.model.auth.response;

import com.tuleninov.web.model.user.KnownAuthorityUI;

import java.util.Set;

/**
 * Record for access data.
 */
public record AccessTokenUIResponse(String accessToken,
                                    String refreshToken,
                                    long expireIn,
                                    Set<KnownAuthorityUI> authorities) {
}
