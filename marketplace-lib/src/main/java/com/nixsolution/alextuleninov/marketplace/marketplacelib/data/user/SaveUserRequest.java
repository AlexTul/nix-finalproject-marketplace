/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplacelib.data.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Record for the user request.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record SaveUserRequest(
        @NotBlank(message = "The first name of user is mandatory")
        @Size(min = 2, max = 30, message = "The first name should be between 2 and 30 characters")
        String firstName,

        @Size(min = 2, max = 30, message = "The last name should be between 2 and 30 characters")
        String lastName,

        @NotBlank(message = "The email of user is mandatory")
        @Email(message = "Email is not correct")
        String email,

        @NotBlank(message = "The password of user is mandatory")
        @Size(min = 8, max = 32, message = "The password should be between 8 and 32 characters")
        String password
) {
}
