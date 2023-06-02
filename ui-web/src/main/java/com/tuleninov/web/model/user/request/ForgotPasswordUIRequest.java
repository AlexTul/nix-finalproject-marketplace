package com.tuleninov.web.model.user.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record ForgotPasswordUIRequest(

        @NotNull(message = "the email must not be null")
        @Email(message = "the email must be a valid email string")
        String email

) {
}
