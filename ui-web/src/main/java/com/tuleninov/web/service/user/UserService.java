package com.tuleninov.web.service.user;

import com.tuleninov.web.feignclient.UserServiceFeignClient;
import com.tuleninov.web.model.user.CustomUserUI;
import com.tuleninov.web.model.user.UserUIStatus;
import com.tuleninov.web.model.user.request.*;
import com.tuleninov.web.service.mail.MailSender;
import com.tuleninov.web.service.mail.MessageMaker;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for User.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class UserService {

    private final UserServiceFeignClient userServiceFeignClient;
    private final MailSender mailSender;
    private final MessageMaker messageMaker;

    public UserService(UserServiceFeignClient userServiceFeignClient, MailSender mailSender, MessageMaker messageMaker) {
        this.userServiceFeignClient = userServiceFeignClient;
        this.mailSender = mailSender;
        this.messageMaker = messageMaker;
    }

    /**
     * Get a new temporary password for the user.
     *
     * @param request request with user`s credentials
     */
    public void getTemporaryPassword(ForgotPasswordUIRequest request) {
        var temporaryPassword = userServiceFeignClient.getTemporaryPassword(request);

        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    request.email(),
                    messageMaker.makeSubjectForgot(),
                    messageMaker.makeMessageForgot(request.email(), temporaryPassword.password()));
        }).start();
    }

    /**
     * Register the user in the database.
     *
     * @param request request with user`s data
     * @return user who was registered
     */
    public CustomUserUI register(SaveUserUIRequest request) {
        var activationCode = UUID.randomUUID();
        var user = CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.register(request, activationCode.toString()));
        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    user.getEmail(),
                    messageMaker.makeSubjectRegister(),
                    messageMaker.makeMessageRegister(activationCode));
        }).start();

        return user;
    }

    /**
     * Get the user by activation code from the database.
     *
     * @param activationCode activation code for this user
     * @return user that has been activated
     */
    public CustomUserUI activate(String activationCode) {
        return CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.activate(activationCode));
    }

    /**
     * Get the user by email from the database in response format.
     *
     * @return user from database
     */
    public CustomUserUI getCurrentUser(String token) {
        return CustomUserUI.fromUserUIResponse(userServiceFeignClient.getCurrentUser(token));
    }

    /**
     * Merge current user in database.
     * Using PUT because Feign don`t support PATCH.
     *
     * @param token   token to access the corresponding endpoint
     * @param request request with user credentials
     * @return user that was merged
     */
    public CustomUserUI mergeCurrentUser(String token, MergeUserUIRequest request) {
        var user = CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.mergeCurrentUser(token, request));
        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    user.getEmail(),
                    messageMaker.makeSubjectMergeUser(),
                    messageMaker.makeMessageMergeUser(user));
        }).start();

        return user;
    }

    /**
     * Change user`s password in database.
     * Using PUT because Feign don`t support PATCH.
     *
     * @param token   token to access the corresponding endpoint
     * @param request request with user credentials
     * @return user who changed the password
     */
    public CustomUserUI changeCurrentUserPassword(String token, ChangeUserUIPasswordRequest request) {
        var user = CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.changeCurrentUserPassword(token, request));
        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    user.getEmail(),
                    messageMaker.makeSubjectChangePassword(),
                    messageMaker.makeMessageChangePasswordCurrentUser(user, request));
        }).start();

        return user;
    }

    /**
     * Delete the current user in the database.
     *
     * @param token token to access the corresponding endpoint
     * @param user  user who deleted the profile
     */
    public void deleteCurrentUser(String token, CustomUserUI user) {
        userServiceFeignClient.deleteCurrentUser(token);
        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    user.getEmail(),
                    messageMaker.makeSubjectDelete(),
                    messageMaker.makeMessageDelete(user));
        }).start();
    }

    /**
     * Get all users from the database with pagination information.
     *
     * @param token    token to access the corresponding endpoint
     * @param pageable abstract interface for pagination information
     * @return all users from database
     */
    public Page<CustomUserUI> listUsers(String token, Pageable pageable) {
        return userServiceFeignClient.listUsers(token, pageable)
                .map(CustomUserUI::fromUserUIResponse);
    }

    /**
     * Register the user as admin in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param request request with new admin data
     * @return user who was registered
     */
    public CustomUserUI registerAdmin(String token, SaveUserUIRequest request) {
        var activationCode = UUID.randomUUID();
        var user = CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.registerAdmin(token, request, activationCode.toString()));
        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    user.getEmail(),
                    messageMaker.makeSubjectRegister(),
                    messageMaker.makeMessageRegister(activationCode));
        }).start();

        return user;
    }

    /**
     * Get the user by id from the database.
     *
     * @param token token to access the corresponding endpoint
     * @param id    id of user
     * @return user from database
     */
    public CustomUserUI getUserById(String token, long id) {
        return CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.getUserById(token, id));
    }

    /**
     * Merge the user in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      id of user
     * @param request request with user credentials
     * @return user that was merged
     */
    public CustomUserUI mergeUserById(String token, long id, MergeUserUIRequest request) {
        var user = CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.mergeUserById(token, id, request));
        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    user.getEmail(),
                    messageMaker.makeSubjectMergeUser(),
                    messageMaker.makeMessageMergeUser(user));
        }).start();

        return user;
    }

    /**
     * Change user`s status in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      id of user
     * @param request request with user credentials
     * @return user whose status was changed
     */
    public CustomUserUI changeUserStatusById(String token, long id, ChangeUserUIStatusRequest request) {
        request = request.status().compareTo(UserUIStatus.ACTIVE) == 0
                ? new ChangeUserUIStatusRequest(UserUIStatus.SUSPENDED)
                : new ChangeUserUIStatusRequest(UserUIStatus.ACTIVE);

        var user = CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.changeUserStatusById(token, id, request));
        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    user.getEmail(),
                    messageMaker.makeSubjectChangeUserStatus(),
                    messageMaker.makeMessageChangeUserStatus(user));
        }).start();

        return user;
    }

    /**
     * Change user`s password in the database.
     *
     * @param token   token to access the corresponding endpoint
     * @param id      id of goods
     * @param request request with user parameters
     * @return user whose password was changed
     */
    public CustomUserUI changePasswordById(String token, long id, OverrideUserUIPasswordRequest request) {
        var user = CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.changeUserPassword(token, id, request));
        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    user.getEmail(),
                    messageMaker.makeSubjectChangePassword(),
                    messageMaker.makeMessageChangePassword(user, request));
        }).start();

        return user;
    }

    /**
     * Delete the user in the database.
     *
     * @param token token to access the corresponding endpoint
     * @param id    id of user
     * @return user what was deleted
     */
    public CustomUserUI deleteUserById(String token, long id) {
        var user = CustomUserUI.fromUserUIResponse(
                userServiceFeignClient.deleteById(token, id));
        var locale = LocaleContextHolder.getLocale();

        new Thread(() -> {
            LocaleContextHolder.setLocale(locale);
            mailSender.send(
                    user.getEmail(),
                    messageMaker.makeSubjectDelete(),
                    messageMaker.makeMessageDelete(user));
        }).start();

        return user;
    }
}
