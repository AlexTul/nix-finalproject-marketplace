package com.tuleninov.web.service.mail;

import com.tuleninov.web.config.mvc.mail.MailProperties;
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
    private final MailProperties mailProperties;

    public MailSender(JavaMailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
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

        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
