/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.role;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Class for exceptions process for Role with specific HTTP response status codes.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public class RoleExceptions {

    public static ResponseStatusException roleNotFound(int roleId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with id " + roleId + " was not found");
    }
}
