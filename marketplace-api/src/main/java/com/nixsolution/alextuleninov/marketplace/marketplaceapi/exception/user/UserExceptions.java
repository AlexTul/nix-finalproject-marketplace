/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for exceptions process for User with specific HTTP response status codes.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class UserExceptions {

    /**
     * Exceptions process for User with specific HTTP response status codes NOT_FOUND.
     *
     * @param userId        id of user
     * @return              specific HTTP response status codes
     */
    public static ResponseStatusException userNotFound(int userId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + userId + " was not found");
    }

    /**
     * Exceptions process for User with specific HTTP response status codes NOT_FOUND.
     *
     * @param email         email of user
     * @return              specific HTTP response status codes
     */
    public static ResponseStatusException userNotFound(String email) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " was not found");
    }

    /**
     * Exceptions process for User with specific HTTP response status codes BAD_REQUEST.
     *
     * @param email         email of user
     * @return              specific HTTP response status codes
     */
    public static ResponseStatusException duplicateEmail(String email) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email " + email + " already taken");
    }
}
