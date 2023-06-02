package com.tuleninov.web.feignclient;

import com.tuleninov.web.Routes;
import com.tuleninov.web.model.auth.request.RefreshTokenUIRequest;
import com.tuleninov.web.model.auth.request.SignInUIRequest;
import com.tuleninov.web.model.auth.response.AccessTokenUIResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.tuleninov.web.config.security.SecurityConstantsUI.AUTH_CLAIM;

/**
 * Feign Client for the Authentication.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@FeignClient(name = "AuthController.class", url = "${services.server.api}")
public interface AuthServiceFeignClient {

    /**
     * Get token to access the corresponding endpoint.
     *
     * @param request request with user`s credentials
     * @return token to access the corresponding endpoint
     */
    @PostMapping(
            value = Routes.API_TOKEN,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    AccessTokenUIResponse login(@RequestBody SignInUIRequest request);

    /**
     * Refresh access token in the server.
     *
     * @param request refresh token from user
     * @return token to access the corresponding endpoint
     * */
    @PostMapping(
            value = Routes.API_TOKEN + "/refresh",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    AccessTokenUIResponse refresh(@RequestBody RefreshTokenUIRequest request);

    /**
     * Invalidate access and refresh token in server.
     *
     * @param token token to access the corresponding endpoint
     * @param request refresh token from user
     * */
    @PostMapping(
            value = Routes.API_TOKEN + "/invalidate",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    void invalidate(@RequestHeader(AUTH_CLAIM) String token,
                    @RequestBody RefreshTokenUIRequest request);
}
