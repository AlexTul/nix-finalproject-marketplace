package com.tuleninov.web.config.mvc.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Class for configuring data from a property file to create messages for the user.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Validated
@Configuration
@ConfigurationProperties(prefix = "spring.mail.message")
public class MessagesProperties {

    @NotEmpty(message = "messageSuccessfulActivation must not be empty")
    private String messageSuccessfulActivation;

    @NotEmpty(message = "messageSuccessfulActivationUk must not be empty")
    private String messageSuccessfulActivationUk;

    @NotEmpty(message = "messagePassword must not be empty")
    private String messagePassword;

    @NotEmpty(message = "messagePasswordUk must not be empty")
    private String messagePasswordUk;

    @NotEmpty(message = "messageFillCaptcha must not be empty")
    private String messageFillCaptcha;

    @NotEmpty(message = "messageFillCaptchaUk must not be empty")
    private String messageFillCaptchaUk;

    @NotEmpty(message = "host must not be empty")
    private String host;

    @NotEmpty(message = "subjectRegister must not be empty")
    private String subjectRegister;

    @NotEmpty(message = "subjectRegisterUk must not be empty")
    private String subjectRegisterUk;

    @NotEmpty(message = "messageRegister must not be empty")
    private String messageRegister;

    @NotEmpty(message = "messageRegisterUk must not be empty")
    private String messageRegisterUk;

    @NotEmpty(message = "subjectStatus must not be empty")
    private String subjectStatus;

    @NotEmpty(message = "subjectStatusUk must not be empty")
    private String subjectStatusUk;

    @NotEmpty(message = "messageStatus must not be empty")
    private String messageStatus;

    @NotEmpty(message = "messageStatusUk must not be empty")
    private String messageStatusUk;

    @NotEmpty(message = "subjectMerge must not be empty")
    private String subjectMerge;

    @NotEmpty(message = "subjectMergeUk must not be empty")
    private String subjectMergeUk;

    @NotEmpty(message = "messageMerge must not be empty")
    private String messageMerge;

    @NotEmpty(message = "messageMergeUk must not be empty")
    private String messageMergeUk;

    @NotEmpty(message = "subjectChangePassword must not be empty")
    private String subjectChangePassword;

    @NotEmpty(message = "subjectChangePasswordUk must not be empty")
    private String subjectChangePasswordUk;

    @NotEmpty(message = "messageChangePassword must not be empty")
    private String messageChangePassword;

    @NotEmpty(message = "messageChangePasswordUk must not be empty")
    private String messageChangePasswordUk;

    @NotEmpty(message = "subjectDelete must not be empty")
    private String subjectDelete;

    @NotEmpty(message = "subjectDeleteUk must not be empty")
    private String subjectDeleteUk;

    @NotEmpty(message = "messageDelete must not be empty")
    private String messageDelete;

    @NotEmpty(message = "messageDeleteUk must not be empty")
    private String messageDeleteUk;

    @NotEmpty(message = "subjectForgot must not be empty")
    private String subjectForgot;

    @NotEmpty(message = "subjectForgotUk must not be empty")
    private String subjectForgotUk;

    @NotEmpty(message = "messageForgot must not be empty")
    private String messageForgot;

    @NotEmpty(message = "messageForgotUk must not be empty")
    private String messageForgotUk;

    public String getMessageSuccessfulActivation() {
        return messageSuccessfulActivation;
    }

    public void setMessageSuccessfulActivation(String messageSuccessfulActivation) {
        this.messageSuccessfulActivation = messageSuccessfulActivation;
    }

    public String getMessageSuccessfulActivationUk() {
        return messageSuccessfulActivationUk;
    }

    public void setMessageSuccessfulActivationUk(String messageSuccessfulActivationUk) {
        this.messageSuccessfulActivationUk = messageSuccessfulActivationUk;
    }

    public String getMessagePassword() {
        return messagePassword;
    }

    public void setMessagePassword(String messagePassword) {
        this.messagePassword = messagePassword;
    }

    public String getMessagePasswordUk() {
        return messagePasswordUk;
    }

    public void setMessagePasswordUk(String messagePasswordUk) {
        this.messagePasswordUk = messagePasswordUk;
    }

    public String getMessageFillCaptcha() {
        return messageFillCaptcha;
    }

    public void setMessageFillCaptcha(String messageFillCaptcha) {
        this.messageFillCaptcha = messageFillCaptcha;
    }

    public String getMessageFillCaptchaUk() {
        return messageFillCaptchaUk;
    }

    public void setMessageFillCaptchaUk(String messageFillCaptchaUk) {
        this.messageFillCaptchaUk = messageFillCaptchaUk;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSubjectRegister() {
        return subjectRegister;
    }

    public void setSubjectRegister(String subjectRegister) {
        this.subjectRegister = subjectRegister;
    }

    public String getSubjectRegisterUk() {
        return subjectRegisterUk;
    }

    public void setSubjectRegisterUk(String subjectRegisterUk) {
        this.subjectRegisterUk = subjectRegisterUk;
    }

    public String getMessageRegister() {
        return messageRegister;
    }

    public void setMessageRegister(String messageRegister) {
        this.messageRegister = messageRegister;
    }

    public String getMessageRegisterUk() {
        return messageRegisterUk;
    }

    public void setMessageRegisterUk(String messageRegisterUk) {
        this.messageRegisterUk = messageRegisterUk;
    }

    public String getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(String subjectStatus) {
        this.subjectStatus = subjectStatus;
    }

    public String getSubjectStatusUk() {
        return subjectStatusUk;
    }

    public void setSubjectStatusUk(String subjectStatusUk) {
        this.subjectStatusUk = subjectStatusUk;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getMessageStatusUk() {
        return messageStatusUk;
    }

    public void setMessageStatusUk(String messageStatusUk) {
        this.messageStatusUk = messageStatusUk;
    }

    public String getSubjectMerge() {
        return subjectMerge;
    }

    public void setSubjectMerge(String subjectMerge) {
        this.subjectMerge = subjectMerge;
    }

    public String getSubjectMergeUk() {
        return subjectMergeUk;
    }

    public void setSubjectMergeUk(String subjectMergeUk) {
        this.subjectMergeUk = subjectMergeUk;
    }

    public String getMessageMerge() {
        return messageMerge;
    }

    public void setMessageMerge(String messageMerge) {
        this.messageMerge = messageMerge;
    }

    public String getMessageMergeUk() {
        return messageMergeUk;
    }

    public void setMessageMergeUk(String messageMergeUk) {
        this.messageMergeUk = messageMergeUk;
    }

    public String getSubjectChangePassword() {
        return subjectChangePassword;
    }

    public void setSubjectChangePassword(String subjectChangePassword) {
        this.subjectChangePassword = subjectChangePassword;
    }

    public String getSubjectChangePasswordUk() {
        return subjectChangePasswordUk;
    }

    public void setSubjectChangePasswordUk(String subjectChangePasswordUk) {
        this.subjectChangePasswordUk = subjectChangePasswordUk;
    }

    public String getMessageChangePassword() {
        return messageChangePassword;
    }

    public void setMessageChangePassword(String messageChangePassword) {
        this.messageChangePassword = messageChangePassword;
    }

    public String getMessageChangePasswordUk() {
        return messageChangePasswordUk;
    }

    public void setMessageChangePasswordUk(String messageChangePasswordUk) {
        this.messageChangePasswordUk = messageChangePasswordUk;
    }

    public String getSubjectDelete() {
        return subjectDelete;
    }

    public void setSubjectDelete(String subjectDelete) {
        this.subjectDelete = subjectDelete;
    }

    public String getSubjectDeleteUk() {
        return subjectDeleteUk;
    }

    public void setSubjectDeleteUk(String subjectDeleteUk) {
        this.subjectDeleteUk = subjectDeleteUk;
    }

    public String getMessageDelete() {
        return messageDelete;
    }

    public void setMessageDelete(String messageDelete) {
        this.messageDelete = messageDelete;
    }

    public String getMessageDeleteUk() {
        return messageDeleteUk;
    }

    public void setMessageDeleteUk(String messageDeleteUk) {
        this.messageDeleteUk = messageDeleteUk;
    }

    public String getSubjectForgot() {
        return subjectForgot;
    }

    public void setSubjectForgot(String subjectForgot) {
        this.subjectForgot = subjectForgot;
    }

    public String getSubjectForgotUk() {
        return subjectForgotUk;
    }

    public void setSubjectForgotUk(String subjectForgotUk) {
        this.subjectForgotUk = subjectForgotUk;
    }

    public String getMessageForgot() {
        return messageForgot;
    }

    public void setMessageForgot(String messageForgot) {
        this.messageForgot = messageForgot;
    }

    public String getMessageForgotUk() {
        return messageForgotUk;
    }

    public void setMessageForgotUk(String messageForgotUk) {
        this.messageForgotUk = messageForgotUk;
    }
}
