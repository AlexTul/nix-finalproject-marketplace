package com.tuleninov.web.controller.login;

import com.tuleninov.web.Routes;
import com.tuleninov.web.model.auth.request.SignInUIRequest;
import com.tuleninov.web.model.auth.response.AccessTokenUIResponse;
import com.tuleninov.web.model.user.KnownAuthorityUI;
import com.tuleninov.web.service.auth.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.time.OffsetDateTime;

import static com.tuleninov.web.AppConstants.*;

/**
 * Controller for the login page.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping(Routes.WEB_TOKEN)
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Get login page.
     *
     * @return login page
     */
    @GetMapping
    public String getLoginPage() {
        return "login/login";
    }

    /**
     * Get token, refresh token, expire in and sendRedirect to relevant page.
     *
     * @param request request with user`s credentials
     * @param req  an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param resp an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     */
    @PostMapping
    public void login(@Valid SignInUIRequest request,
                      HttpServletRequest req,
                      HttpServletResponse resp) throws IOException {

        AccessTokenUIResponse token = authService.login(request);
        OffsetDateTime controlDateTime = OffsetDateTime.now().plusSeconds(token.expireIn());

        HttpSession session = req.getSession();
        session.setAttribute(SCOPE_LOGIN, request.login());
        session.setAttribute(SCOPE_ACCESS_TOKEN, token.accessToken());
        session.setAttribute(SCOPE_REFRESH_TOKEN, token.refreshToken());
        session.setAttribute(SCOPE_CONTROL_DATE_TIME_TOKEN, controlDateTime);

        if (token.authorities().contains(KnownAuthorityUI.ROLE_ADMIN)
                && token.authorities().contains(KnownAuthorityUI.ROLE_USER)) {
            req.getSession().setAttribute(SCOPE_HEADER_CONTENT, "header-admin");
            log.info("User '" + request.login() + "' logged in.");
            resp.sendRedirect(Routes.WEB_INDEX);
        } else if (!token.authorities().contains(KnownAuthorityUI.ROLE_ADMIN)
                && token.authorities().contains(KnownAuthorityUI.ROLE_USER)) {
            req.getSession().setAttribute(SCOPE_HEADER_CONTENT, "header-user");
            log.info("User '" + request.login() + "' logged in.");
            resp.sendRedirect(Routes.WEB_INDEX);
        } else {
            log.info("User '" + request.login() + "' redirected on LogIn page");
            resp.sendRedirect(Routes.WEB_TOKEN);
        }
    }
}
