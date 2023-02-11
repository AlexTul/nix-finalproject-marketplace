/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.user;

import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.user.SaveUserRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.user.UserUIResponse;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.feignclient.UserServiceFeignClient;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.user.UserUI;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for User.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class UserService {

    @Value("${services.marketplace.messages}")
    private String messages;

    private final UserServiceFeignClient userServiceFeignClient;
    private final MailSender mailSender;

    public UserService(UserServiceFeignClient userServiceFeignClient, MailSender mailSender) {
        this.userServiceFeignClient = userServiceFeignClient;
        this.mailSender = mailSender;
    }

    /**
     * Create the user in the database.
     *
     * @param request       request with user parameters
     */
    public void register(SaveUserRequest request) {
        userServiceFeignClient.register(request);

        if (!Strings.isBlank(request.email())) {
            mailSender.send(
                    request.email(),
                    "Activation link",
                    UserServiceMessagesMaker.makeWelcomeMessage(request, messages));
        }
    }

    /**
     * Find all users from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all users from database in response format
     */
    public Page<UserUIResponse> findAll(Pageable pageable) {
        return userServiceFeignClient.getAll(pageable);
    }

    /**
     * Find the user by email from the database in response format.
     *
     * @param email         email of user
     * @return              the user from database in response format
     */
    public UserUI findByEmail(String email) {
        return UserUI.fromUserResponse(
                userServiceFeignClient.getCurrentUser(email));
    }

    /**
     * Exists user by email in the database in boolean format.
     *
     * @param email         email of user
     * @return              true - if user exists in database and false - is user does not exist in database
     */
    public boolean existsByEmail(String email) {
        return userServiceFeignClient.existsByEmail(email);
    }

    /**
     * Update the user in the database.
     *
     * @param id            id of goods
     * @param request       request with user parameters
     */
    public void update(int id, SaveUserRequest request) {
        var userEmail = userServiceFeignClient.getUserById(id).email();

        userServiceFeignClient.update(id, request);
        mailSender.send(
                userEmail,
                "Changing your data in Market Place",
                UserServiceMessagesMaker.makeUpdateMessage(request));
    }

    /**
     * Delete the user in the database.
     *
     * @param email         email of user
     */
    public void delete(String email) {
        var userUI = findByEmail(email);

        for (var variable : userUI.getRoles()) {
            if (!variable.getName().equals("ROLE_ADMIN")) {
                userServiceFeignClient.delete(email);
                mailSender.send(
                        userUI.getEmail(),
                        "Deleting your profile in Market Place",
                        UserServiceMessagesMaker.makeDeleteMessage());
            }
        }
    }
}
