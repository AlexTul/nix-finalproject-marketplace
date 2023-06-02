package com.tuleninov.serverapi.service.auth;

import com.tuleninov.serverapi.exceptions.auth.InvalidRefreshTokenException;
import com.tuleninov.serverapi.model.auth.CustomUserDetails;
import com.tuleninov.serverapi.model.auth.response.AccessTokenResponse;

public interface AuthOperations {

    AccessTokenResponse getToken(CustomUserDetails userDetails);

    AccessTokenResponse refreshToken(String refreshToken)
            throws InvalidRefreshTokenException;

    void invalidateToken(String refreshToken, String ownerEmail) throws InvalidRefreshTokenException;

}
