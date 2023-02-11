/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service class for mail sending.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class MailSender {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public MailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Find all users from database in response format with pagination information.
     *
     * @param emailTo       email of user
     * @param subject       subject of message
     * @param message       message for user about action
     */
    public void send(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
