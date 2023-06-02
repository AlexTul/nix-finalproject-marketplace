package com.tuleninov.web.service.auth;

import com.tuleninov.web.feignclient.AuthServiceFeignClient;
import com.tuleninov.web.model.auth.request.RefreshTokenUIRequest;
import com.tuleninov.web.model.auth.request.SignInUIRequest;
import com.tuleninov.web.model.auth.response.AccessTokenUIResponse;
import org.springframework.stereotype.Service;

/**
 * Service class for Authentication.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class AuthService {

    private final AuthServiceFeignClient authServiceFeignClient;

    public AuthService(AuthServiceFeignClient authServiceFeignClient) {
        this.authServiceFeignClient = authServiceFeignClient;
    }

    /**
     * Get the token to access the corresponding endpoint.
     *
     * @param request request with user`s credentials
     * @return token for access to server resources
     */
    public AccessTokenUIResponse login(SignInUIRequest request) {
        return authServiceFeignClient.login(request);
    }

    /**
     * Refresh access token in the server.
     *
     * @param request refresh token from user
     * @return token to access the corresponding endpoint
     * */
    public AccessTokenUIResponse refresh(RefreshTokenUIRequest request) {
        return authServiceFeignClient.refresh(request);
    }

    /**
     * Invalidate the refresh token in the server.
     *
     * @param token token to access the corresponding endpoint
     * @param request request with refresh token
     */
    public void invalidate(String token, RefreshTokenUIRequest request) {
        authServiceFeignClient.invalidate(token, request);
    }
}
