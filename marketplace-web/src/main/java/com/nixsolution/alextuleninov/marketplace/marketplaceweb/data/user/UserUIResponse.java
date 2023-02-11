/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.data.user;

/**
 * Record for the user response.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record UserUIResponse(

        int id,

        String firstName,

        String lastName,

        String email,

        String password,

        int roleId,

        String roleName
) {
}
