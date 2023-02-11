/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.user;

import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.user.SaveUserRequest;

/**
 * Service class for making message.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class UserServiceMessagesMaker {

    /**
     * Create message for user about successful registration.
     *
     * @param request       request with user parameters
     * @return              message about successful registration
     */
    public static String makeWelcomeMessage(SaveUserRequest request, String localhost) {
        return "Dear user,\n welcome to MarketPlace. Please, visit next link: " + localhost + "/activate/"
                + request.email();
    }

    /**
     * Create message for user about updating your credential.
     *
     * @param request       request with user parameters
     * @return              message about updating user`s credential
     */
    public static String makeUpdateMessage(SaveUserRequest request) {
        return String.format("""
                        Dear user, your data was change in Market Place.
                        Your first name is: %s, last name is: %s, password is: %s.
                        Best Regards,
                        Market Place""",
                request.firstName(),
                request.lastName(),
                request.password());
    }

    /**
     * Create message for user about updating your credential.
     *
     * @return              message about deleting user from database
     */
    public static String makeDeleteMessage() {
        return """
                Dear user, your profile was delete in Market Place.
                Best Regards,
                Market Place""";
    }
}
