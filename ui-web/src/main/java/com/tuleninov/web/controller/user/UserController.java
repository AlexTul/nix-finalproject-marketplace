package com.tuleninov.web.controller.user;

import com.tuleninov.web.config.mvc.captcha.CaptchaProperties;
import com.tuleninov.web.config.pagination.ConfigDTO;
import com.tuleninov.web.config.pagination.PaginationConfig;
import com.tuleninov.web.controller.TokenProvider;
import com.tuleninov.web.model.captcha.CaptchaResponse;
import com.tuleninov.web.model.user.CustomUserUI;
import com.tuleninov.web.model.user.UserUIStatus;
import com.tuleninov.web.model.user.request.*;
import com.tuleninov.web.service.mail.PageMessageMaker;
import com.tuleninov.web.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

import static com.tuleninov.web.AppConstants.*;
import static com.tuleninov.web.Routes.*;

/**
 * Controller for user registration, authenticated user API, admin-only API.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping(value = WEB_USERS)
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final PageMessageMaker pageMessageMaker;
    private final CaptchaProperties captchaProperties;
    private final RestTemplate restTemplate;

    public UserController(TokenProvider tokenProvider, UserService userService, PageMessageMaker pageMessageMaker,
                          CaptchaProperties captchaProperties, RestTemplate restTemplate) {
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.pageMessageMaker = pageMessageMaker;
        this.captchaProperties = captchaProperties;
        this.restTemplate = restTemplate;
    }

    //region user forgot password

    /**
     * Get forgot password page.
     *
     * @return forgot password page
     */
    @GetMapping(value = "/forgot")
    public String getForgotPasswordPage() {
        return "forgot/forgot-password";
    }

    /**
     * Get a new temporary password for the user.
     *
     * @param request request with user`s credentials
     * @return index page
     */
    @PatchMapping(value = "/forgot")
    public String getTemporaryPassword(@Valid ForgotPasswordUIRequest request) {
        userService.getTemporaryPassword(request);

        log.info("User with email: '" + request.email() + "' received the temporary password.");

        return "redirect:" + WEB_INDEX;
    }

    //endregion

    //region user registration

    /**
     * Get register user page.
     *
     * @param model holder for model attributes
     * @return user page
     */
    @GetMapping(value = "/register")
    public String getRegisterUserPage(Model model) {
        model.addAttribute(SCOPE_SITE_KEY, captchaProperties.getSiteKey());

        return "register/register-user";
    }

    /**
     * Register user as client in database.
     *
     * @param request            request with admin`s credentials
     * @param redirectAttributes the given flash attribute
     * @return index page
     */
    @PostMapping
    public String register(@Valid SaveUserUIRequest request,
                           @RequestParam("g-recaptcha-response") String captchaResponse,
                           RedirectAttributes redirectAttributes) {
        if (!request.password().equals(request.confirmPassword())) {
            log.info("Password and confirmation password do not match");

            redirectAttributes.addFlashAttribute(SCOPE_MESSAGE_ERROR_PASSWORD_NOT_MATCH, pageMessageMaker.makePageMessagePassword());

            return "redirect:" + WEB_USERS + "/register";
        }

        var response = getResponseForObject(captchaResponse);
        if (!response.isSuccess()) {
            log.info("Fill in the captcha");

            redirectAttributes.addFlashAttribute(SCOPE_CAPTCHA_ERROR, pageMessageMaker.makePageMessageFillCaptcha());

            return "redirect:" + WEB_USERS + "/register";
        }

        var user = userService.register(request);

        log.info("User with email: '" + user.getEmail() + "' has been added to database.");

        return "redirect:" + WEB_INDEX;
    }

    /**
     * Activate the user in the database.
     *
     * @param code  activation code from user
     * @param model holder for model attributes
     * @return index page
     */
    @GetMapping("/{code}/activate")
    public String activate(@PathVariable(value = "code") String code,
                           Model model) {
        var userByActivationCode = userService.activate(code);

        log.info("User '" + userByActivationCode.getEmail() + "' has been activated.");

        model.addAttribute(SCOPE_MESSAGE, pageMessageMaker.makePageMessageSucReg());

        return "register/register-success";
    }

    //endregion

    //region admin-only API

    /**
     * Get user`s page.
     *
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param model holder for model attributes
     * @return user page
     */
    @GetMapping()
    public String getUsersPage(HttpServletRequest req,
                               Model model) {
        var token = tokenProvider.provideTokenForHeader(req);
        var config = PaginationConfig.config(req);
        var users = getCustomUsers(token, config);

        model.addAttribute(SCOPE_OBJECTS, users);

        return "user/user";
    }

    /**
     * Get register admin page.
     *
     * @return user page
     */
    @GetMapping(value = "/admins")
    public String getRegisterAdminPage() {
        return "register/register-admin";
    }

    /**
     * Register user as admin in database.
     *
     * @param request            request with admin`s credentials
     * @param req                an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param redirectAttributes the given flash attribute
     * @return users page
     */
    @PostMapping(value = "/admins")
    public String registerAdmin(@Valid SaveUserUIRequest request,
                                HttpServletRequest req,
                                RedirectAttributes redirectAttributes) {
        if (!request.password().equals(request.confirmPassword())) {
            log.info("Password and confirmation password do not match");

            redirectAttributes.addFlashAttribute(SCOPE_MESSAGE_ERROR_PASSWORD_NOT_MATCH, pageMessageMaker.makePageMessagePassword());

            return "redirect:" + WEB_USERS + "/admins";
        }

        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.registerAdmin(token, request);

        log.info("Admin with email: '" + user.getEmail() + "' has been added to database.");

        return "redirect:" + WEB_USERS;
    }

    /**
     * Get merge user`s page.
     *
     * @param id    id of user
     * @param model holder for model attributes
     * @param req   an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return update page
     */
    @GetMapping(value = "/{id}")
    public String getMergeUserPage(@PathVariable(value = "id") long id,
                                   HttpServletRequest req,
                                   Model model) {
        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.getUserById(token, id);

        model.addAttribute(SCOPE_USER, user);

        return "user/user-merge";
    }

    /**
     * Merge user by id in database.
     *
     * @param id      id of user
     * @param request request with user`s credentials
     * @param req     an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return users page
     */
    @PatchMapping(value = "/{id}")
    public String mergeUserById(@PathVariable(value = "id") long id,
                                @Valid MergeUserUIRequest request,
                                HttpServletRequest req) {
        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.mergeUserById(token, id, request);

        log.info("Data user: '" + user.getEmail() + "' were merged.");

        return "redirect:" + WEB_USERS;
    }

    /**
     * Change user`s status by id in database.
     *
     * @param id     id of user
     * @param status user`s status
     * @param req    an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return update page
     */
    @PatchMapping(value = "/{id}/status")
    public String changeUserStatusById(@PathVariable(value = "id") long id,
                                       @RequestParam(value = "status") UserUIStatus status,
                                       HttpServletRequest req) {
        var request = new ChangeUserUIStatusRequest(status);
        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.changeUserStatusById(token, id, request);

        log.info("Status for user: '" + user.getEmail() + "' was changed.");

        return "redirect:" + WEB_USERS;
    }

    /**
     * Get user password update page.
     *
     * @param id    id of user
     * @param model holder for model attributes
     * @return update page
     */
    @GetMapping(value = "/{id}/password")
    public String getChangePasswordPage(@PathVariable(value = "id") long id,
                                        Model model) {
        model.addAttribute(SCOPE_USER_ID, id);

        return "user/user-change-password";
    }

    /**
     * Change user`s password by id in database.
     *
     * @param id                 id of user
     * @param request            with password and confirm password
     * @param req                an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @param redirectAttributes the given flash attribute
     * @return users page
     */
    @PatchMapping(value = "/{id}/password")
    public String changePasswordById(@PathVariable(value = "id") long id,
                                     @Valid OverrideUserUIPasswordRequest request,
                                     HttpServletRequest req,
                                     RedirectAttributes redirectAttributes) {
        if (!request.password().equals(request.confirmPassword())) {
            log.info("Password and confirmation password do not match");

            redirectAttributes.addFlashAttribute(SCOPE_MESSAGE_ERROR_PASSWORD_NOT_MATCH, pageMessageMaker.makePageMessagePassword());
            redirectAttributes.addFlashAttribute(SCOPE_USER_ID, id);

            return "redirect:" + WEB_USERS + "/" + id + "/password";
        }

        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.changePasswordById(token, id, request);

        log.info("Password for user: '" + user.getEmail() + "' was changed.");

        return "redirect:" + WEB_USERS;
    }

    /**
     * Delete the user by id in the database.
     *
     * @param id  the id of user
     * @param req an object that is passed as an argument to the servlet's utility methods (doGet, doPost, etc.)
     * @return users page or index - if it is admin
     */
    @DeleteMapping(value = "/{id}")
    public String deleteUserById(@PathVariable(value = "id") long id,
                                 HttpServletRequest req) {
        var token = tokenProvider.provideTokenForHeader(req);
        var user = userService.deleteUserById(token, id);

        log.info("User '" + user.getEmail() + "' was deleted.");

        var login = req.getSession().getAttribute(SCOPE_LOGIN);

        return user.getEmail().equals(login) ? "redirect:" + WEB_LOGOUT : "redirect:" + WEB_USERS;
    }

    /**
     * Get the users from the database.
     * (If the user enters an incorrect page number, which is greater than the actual one,
     * into the address bar, it will redirect to the last valid page).
     *
     * @param token  token to access the corresponding endpoint
     * @param config pagination config
     * @return users
     */
    private Page<CustomUserUI> getCustomUsers(String token, ConfigDTO config) {
        var users = userService.listUsers(token, PageRequest.of(config.page(), config.size()));

        var totalPages = users.getTotalPages();
        if (totalPages > 0 && config.page() + 1 > totalPages) {
            users = userService.listUsers(token, PageRequest.of(totalPages - 1, config.size()));
        }

        return users;
    }

    /**
     * Get post response for the object.
     *
     * @param captchaResponse the response from user
     * @return users
     */
    private CaptchaResponse getResponseForObject(String captchaResponse) {
        var url = String.format(captchaProperties.getUrl(), captchaProperties.getSecret(), captchaResponse);
        return restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponse.class);
    }

    //endregion
}
