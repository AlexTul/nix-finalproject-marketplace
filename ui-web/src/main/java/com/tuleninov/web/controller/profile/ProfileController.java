package com.tuleninov.web.controller.profile;

import com.tuleninov.web.Routes;
import com.tuleninov.web.controller.TokenProvider;
import com.tuleninov.web.model.user.request.ChangeUserUIPasswordRequest;
import com.tuleninov.web.model.user.request.MergeUserUIRequest;
import com.tuleninov.web.service.mail.PageMessageMaker;
import com.tuleninov.web.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.tuleninov.web.AppConstants.SCOPE_MESSAGE_ERROR_PASSWORD_NOT_MATCH;
import static com.tuleninov.web.AppConstants.SCOPE_USER;

/**
 * Controller for the authenticated client (Profile page).
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping(Routes.WEB_PROFILE)
public class ProfileController {

    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final PageMessageMaker messageMaker;

    public ProfileController(TokenProvider tokenProvider, UserService userService, PageMessageMaker messageMaker) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.messageMaker = messageMaker;
    }

    /**
     * Get the client`s profile page.
     *
     * @param req an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return the client`s profile page
     */
    @GetMapping
    public String getProfilePage(HttpServletRequest req,
                                 Model model) {
        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.getCurrentUser(token);

        model.addAttribute(SCOPE_USER, user);

        return "profile/profile";
    }

    /**
     * Get merge current user`s page.
     *
     * @param model holder for model attributes
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return update page
     */
    @GetMapping(value = "/me")
    public String getMergeCurrentUserPage(HttpServletRequest req,
                                          Model model) {
        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.getCurrentUser(token);

        model.addAttribute(SCOPE_USER, user);

        return "profile/profile-merge";
    }

    /**
     * Merge current user in database.
     *
     * @param request request with user`s credentials
     * @param req     an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return current user`s page
     */
    @PatchMapping(value = "/me")
    public String mergeCurrentUser(@Valid MergeUserUIRequest request,
                                   HttpServletRequest req) {
        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.mergeCurrentUser(token, request);

        log.info("Data user: '" + user.getEmail() + "' were merged.");

        return "redirect:" + Routes.WEB_PROFILE;
    }

    /**
     * Get current user`s password update page.
     *
     * @return update page
     */
    @GetMapping(value = "/me/password")
    public String getChangeCurrentUserPasswordPage() {
        return "profile/profile-change-password";
    }

    /**
     * Change current user`s password in database.
     *
     * @param request            with password and confirm password
     * @param req                an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param redirectAttributes the given flash attribute
     * @return user`s page
     */
    @PatchMapping(value = "/me/password")
    public String changeCurrentUserPassword(@Valid ChangeUserUIPasswordRequest request,
                                            HttpServletRequest req,
                                            RedirectAttributes redirectAttributes) {
        if (!request.newPassword().equals(request.confirmPassword())) {
            log.info("New password and confirmation password do not match.");

            redirectAttributes.addFlashAttribute(SCOPE_MESSAGE_ERROR_PASSWORD_NOT_MATCH, messageMaker.makePageMessagePassword());

            return "profile/profile-change-password";
        }

        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.changeCurrentUserPassword(token, request);

        log.info("Password for user: '" + user.getEmail() + "' was changed.");

        return "redirect:" + Routes.WEB_PROFILE;
    }

    /**
     * Delete current user from database.
     *
     * @param req an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return login page
     */
    @DeleteMapping(value = "/me")
    public String deleteCurrentUser(HttpServletRequest req) {
        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.getCurrentUser(token);
        userService.deleteCurrentUser(token, user);

        log.info("User '" + user.getEmail() + "' was deleted.");

        return "redirect:" + Routes.WEB_LOGOUT;
    }
}
