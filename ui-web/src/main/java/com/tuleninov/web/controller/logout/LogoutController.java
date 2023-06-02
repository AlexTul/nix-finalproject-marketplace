package com.tuleninov.web.controller.logout;

import com.tuleninov.web.Routes;
import com.tuleninov.web.controller.TokenProvider;
import com.tuleninov.web.model.auth.request.RefreshTokenUIRequest;
import com.tuleninov.web.service.auth.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.tuleninov.web.AppConstants.*;

/**
 * Controller for the logout page.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping(Routes.WEB_LOGOUT)
public class LogoutController {

    private static final Logger log = LoggerFactory.getLogger(LogoutController.class);
    private final TokenProvider tokenProvider;
    private final AuthService authService;

    public LogoutController(TokenProvider tokenProvider, AuthService authService) {
        this.tokenProvider = tokenProvider;
        this.authService = authService;
    }

    /**
     * Log out.
     *
     * @param req  an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     */
    @GetMapping
    public String getIndexPage(HttpServletRequest req) {

        String refreshToken = (String) req.getSession().getAttribute(SCOPE_REFRESH_TOKEN);
        String login = (String) req.getSession().getAttribute(SCOPE_LOGIN);

        var token = tokenProvider.provideTokenForHeader(req);
        authService.invalidate(token, new RefreshTokenUIRequest(refreshToken));

        HttpSession session = req.getSession();
        session.removeAttribute(SCOPE_LOGIN);
        session.removeAttribute(SCOPE_ACCESS_TOKEN);
        session.removeAttribute(SCOPE_REFRESH_TOKEN);
        session.removeAttribute(SCOPE_CONTROL_DATE_TIME_TOKEN);
        session.removeAttribute(SCOPE_HEADER_CONTENT);

        log.info("User '" + login + "' logged out.");

        return "redirect:" + Routes.WEB_INDEX;
    }
}
