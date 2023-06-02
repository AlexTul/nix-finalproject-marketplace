package com.tuleninov.serverapi.controller.auth;

import com.tuleninov.serverapi.Routes;
import com.tuleninov.serverapi.exceptions.UserExceptions;
import com.tuleninov.serverapi.exceptions.auth.InvalidRefreshTokenException;
import com.tuleninov.serverapi.model.auth.CustomUserDetails;
import com.tuleninov.serverapi.model.auth.request.RefreshTokenRequest;
import com.tuleninov.serverapi.model.auth.request.SignInRequest;
import com.tuleninov.serverapi.model.auth.response.AccessTokenResponse;
import com.tuleninov.serverapi.service.auth.AuthOperations;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for Authentication.
 */
@RestController
@RequestMapping(Routes.TOKEN)
public class AuthController {

    private final AuthOperations authOperations;

    public AuthController(AuthOperations authOperations) {
        this.authOperations = authOperations;
    }

    /*
     * JWTAuthenticationFilter sets the principle (user-details from UserService) using auth manager
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = SignInRequest.class)))
    public AccessTokenResponse login(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return authOperations.getToken(userDetails);
    }

    /**
     * Refresh access token in the server.
     *
     * @param request refresh token from user
     * @return token to access the corresponding endpoint
     * */
    @PostMapping(
            value = "/refresh",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public AccessTokenResponse refresh(@RequestBody @Valid RefreshTokenRequest request) {
        try {
            return authOperations.refreshToken(request.refreshToken());
        } catch (InvalidRefreshTokenException e) {
            throw UserExceptions.invalidRefreshToken(e);
        }
    }

    /**
     * Invalidate the refresh token in the server.
     *
     * @param request refresh token from user
     * */
    @PostMapping(
            value = "/invalidate",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidate(@RequestBody @Valid RefreshTokenRequest request, @AuthenticationPrincipal String email) {
        try {
            authOperations.invalidateToken(request.refreshToken(), email);
        } catch (InvalidRefreshTokenException e) {
            throw UserExceptions.invalidRefreshToken(e);
        }
    }
}
