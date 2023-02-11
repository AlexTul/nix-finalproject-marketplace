/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.user;

import com.nixsolution.alextuleninov.marketplace.marketplaceapi.model.user.User;

/**
 * Record for the user response.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record UserResponse(

        int id,

        String firstName,

        String lastName,

        String email,

        String password,

        int roleId,

        String roleName
) {

    /**
     * Create the new record from User.
     *
     * @param user          user
     * @return              new record from User
     */
    public static UserResponse fromUser(User user) {
        int roleId = user.getRoles()
                .stream()
                .toList()
                .get(0)
                .getId();

        String roleName = user.getRoles()
                .stream()
                .toList()
                .get(0)
                .getName();

        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                roleId,
                roleName
        );
    }
}
