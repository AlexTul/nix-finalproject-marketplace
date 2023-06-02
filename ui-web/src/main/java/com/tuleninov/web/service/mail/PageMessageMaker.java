package com.tuleninov.web.service.mail;

import com.tuleninov.web.config.mvc.mail.MessagesProperties;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service class for creating messages for user`s page.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class PageMessageMaker {

    private final MessagesProperties makerConfig;

    public PageMessageMaker(MessagesProperties makerConfig) {
        this.makerConfig = makerConfig;
    }

    /**
     * Create subject message for user`s page about successful activation.
     *
     * @return message
     */
    public String makePageMessageSucReg() {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? makerConfig.getMessageSuccessfulActivation()
                : makerConfig.getMessageSuccessfulActivationUk();
    }

    /**
     * Create subject message for user`s page about successful activation.
     *
     * @return message
     */
    public String makePageMessagePassword() {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? makerConfig.getMessagePassword()
                : makerConfig.getMessagePasswordUk();
    }

    /**
     * Create subject message for user`s page about successful activation.
     *
     * @return message
     */
    public String makePageMessageFillCaptcha() {
        return LocaleContextHolder.getLocale().toString().equals("en")
                ? makerConfig.getMessageFillCaptcha()
                : makerConfig.getMessageFillCaptchaUk();
    }
}
