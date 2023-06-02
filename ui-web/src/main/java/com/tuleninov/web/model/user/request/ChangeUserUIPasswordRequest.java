package com.tuleninov.web.model.user.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record ChangeUserUIPasswordRequest(

        @NotNull(message = "old password must not be null")
        String oldPassword,

        @NotBlank(message = "the password must not be blank")
        @Size(min = 8, message = "the password's length must be at least 8")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "the password must contain at " +
                "least one number, at least one lowercase letter, at least one uppercase letter, must not contain spaces")
        String newPassword,

        @NotBlank(message = "the confirm password must not be blank")
        @Size(min = 8, message = "the confirm password's length must be at least 8")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "the password must contain at " +
                "least one number, at least one lowercase letter, at least one uppercase letter, must not contain spaces")
        String confirmPassword

) {
}
