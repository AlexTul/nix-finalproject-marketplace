package com.tuleninov.web.service.mail;

import com.tuleninov.web.config.mvc.mail.MessagesProperties;
import com.tuleninov.web.model.user.request.ChangeUserUIPasswordRequest;
import com.tuleninov.web.model.user.request.OverrideUserUIPasswordRequest;
import com.tuleninov.web.model.user.CustomUserUI;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for creating messages for user`s email.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class MessageMaker {

    private final MessagesProperties makerConfig;

    public MessageMaker(MessagesProperties makerConfig) {
        this.makerConfig = makerConfig;
    }

    /**
     * Create subject message for user about successful registration.
     *
     * @return message
     */
    public String makeSubjectRegister() {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? makerConfig.getSubjectRegister()
                : makerConfig.getSubjectRegisterUk();
    }

    /**
     * Create mail message for user about successful registration.
     *
     * @param activationCode activation code
     * @return mail message
     */
    public String makeMessageRegister(UUID activationCode) {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? String.format(makerConfig.getMessageRegister(), makerConfig.getHost(), activationCode.toString())
                : String.format(makerConfig.getMessageRegisterUk(), makerConfig.getHost(), activationCode.toString());
    }

    /**
     * Create subject message for user about merging his data in database.
     *
     * @return subject message
     */
    public String makeSubjectMergeUser() {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? makerConfig.getSubjectMerge()
                : makerConfig.getSubjectMergeUk();
    }

    /**
     * Create mail message for user about merging his data in database.
     *
     * @param user user with user parameters
     * @return mail message
     */
    public String makeMessageMergeUser(CustomUserUI user) {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? String.format(makerConfig.getMessageMerge(), user.getEmail(), user.getNickname())
                : String.format(makerConfig.getMessageMergeUk(), user.getEmail(), user.getNickname());
    }

    /**
     * Create subject message for user about changing his status in database.
     *
     * @return subject message
     */
    public String makeSubjectChangeUserStatus() {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? makerConfig.getSubjectStatus()
                : makerConfig.getSubjectStatusUk();
    }

    /**
     * Create mail message for user about changing his status in database.
     *
     * @param user user with user parameters
     * @return mail message
     */
    public String makeMessageChangeUserStatus(CustomUserUI user) {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? String.format(makerConfig.getMessageStatus(), user.getStatus().toString())
                : String.format(makerConfig.getMessageStatusUk(), user.getStatus().toString());
    }

    /**
     * Create subject message for user about changing his password in database.
     *
     * @return subject message
     */
    public String makeSubjectChangePassword() {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? makerConfig.getSubjectChangePassword()
                : makerConfig.getSubjectChangePasswordUk();
    }

    /**
     * Create mail message for user about changing his password in database.
     *
     * @param user    user with user parameters
     * @param request request with user parameters
     * @return mail message
     */
    public String makeMessageChangePassword(CustomUserUI user, OverrideUserUIPasswordRequest request) {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? String.format(makerConfig.getMessageChangePassword(), user.getEmail(), user.getNickname(), request.password())
                : String.format(makerConfig.getMessageChangePasswordUk(), user.getEmail(), user.getNickname(), request.password());
    }

    /**
     * Create mail message for current user about changing his password in database.
     *
     * @param user    user with user parameters
     * @param request request with user parameters
     * @return mail message
     */
    public String makeMessageChangePasswordCurrentUser(CustomUserUI user, ChangeUserUIPasswordRequest request) {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? String.format(makerConfig.getMessageMerge(), user.getEmail(), user.getNickname(), request.newPassword())
                : String.format(makerConfig.getMessageMergeUk(), user.getEmail(), user.getNickname(), request.newPassword());
    }

    /**
     * Create subject message for user about deleting his profile in database.
     *
     * @return subject message
     */
    public String makeSubjectDelete() {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? makerConfig.getSubjectDelete()
                : makerConfig.getSubjectDeleteUk();
    }

    /**
     * Create mail message for user about deleting his profile in database.
     *
     * @param user user with user`s parameters
     * @return mail message
     */
    public String makeMessageDelete(CustomUserUI user) {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? String.format(makerConfig.getMessageDelete(), user.getEmail(), user.getNickname())
                : String.format(makerConfig.getMessageDeleteUk(), user.getEmail(), user.getNickname());
    }

    /**
     * Create a message topic for a user about a forgotten password.
     *
     * @return subject message
     */
    public String makeSubjectForgot() {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? makerConfig.getSubjectForgot()
                : makerConfig.getSubjectForgotUk();
    }

    /**
     * Create a forgotten password message for the user.
     *
     * @param email             user`s email
     * @param temporaryPassword temporary password
     * @return mail message
     */
    public String makeMessageForgot(String email, String temporaryPassword) {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? String.format(makerConfig.getMessageForgot(), email, temporaryPassword)
                : String.format(makerConfig.getMessageForgotUk(), email, temporaryPassword);
    }
}
