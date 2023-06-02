package com.tuleninov.web.model.auth.request;

import javax.validation.constraints.*;

/**
 * Record for login.
 * */
public record SignInUIRequest(

        @NotNull(message = "email must not be null")
        String login,

        @NotBlank(message = "The password of user is mandatory")
        @Size(min = 8, message = "The password should be between 8 and 70 characters")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "The password does not match the pattern.")
        String password

) {
}
