package com.tuleninov.web.controller;

import com.tuleninov.web.model.auth.request.RefreshTokenUIRequest;
import com.tuleninov.web.model.auth.response.AccessTokenUIResponse;
import com.tuleninov.web.service.auth.AuthService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.OffsetDateTime;

import static com.tuleninov.web.AppConstants.*;
import static com.tuleninov.web.config.security.SecurityConstantsUI.AUTH_TOKEN_PREFIX;

/**
 * Class for providing the access token from the server.
 */
@Component
public class TokenProvider {

    private final AuthService authService;

    public TokenProvider(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Provide the access token from the server.
     *
     * @param req an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return access token for header
     */
    public String provideTokenForHeader(HttpServletRequest req) {
        var accessToken = (String) req.getSession().getAttribute(SCOPE_ACCESS_TOKEN);
        var refreshToken = (String) req.getSession().getAttribute(SCOPE_REFRESH_TOKEN);
        var controlDateTime = (OffsetDateTime) req.getSession().getAttribute(SCOPE_CONTROL_DATE_TIME_TOKEN);

        var now = OffsetDateTime.now();
        var token = controlDateTime.compareTo(now) > 0
                ? accessToken
                : refreshAccessToken(req, refreshToken);

        return AUTH_TOKEN_PREFIX.concat(token);
    }

    /**
     * Refresh access token in the server.
     *
     * @param req          an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param refreshToken refresh token from user
     * @return access token to access the corresponding endpoint
     */
    private String refreshAccessToken(HttpServletRequest req, String refreshToken) {
        var newTokenResponse = authService.refresh(new RefreshTokenUIRequest(refreshToken));
        putScopeToken(req, newTokenResponse);

        return newTokenResponse.accessToken();
    }

    /**
     * Put token`s data to application scope.
     *
     * @param req           an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param tokenResponse token`s data
     */
    private void putScopeToken(HttpServletRequest req, AccessTokenUIResponse tokenResponse) {
        OffsetDateTime controlDateTime = OffsetDateTime.now().plusSeconds(tokenResponse.expireIn());

        HttpSession session = req.getSession();
        session.setAttribute(SCOPE_ACCESS_TOKEN, tokenResponse.accessToken());
        session.setAttribute(SCOPE_REFRESH_TOKEN, tokenResponse.refreshToken());
        session.setAttribute(SCOPE_CONTROL_DATE_TIME_TOKEN, controlDateTime);
    }
}
