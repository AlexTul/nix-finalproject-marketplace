package com.tuleninov.web.model.user.request;

import javax.validation.constraints.*;

/**
 * Record for the user request.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record SaveUserUIRequest(

        @NotNull(message = "the email must not be null")
        @Email(message = "the email must be a valid email string")
        String email,

        @NotBlank(message = "the password must not be blank")
        @Size(min = 8, message = "password's length must be at least 8")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "the password must contain at " +
                "least one number, at least one lowercase letter, at least one uppercase letter, must not contain spaces")
        String password,

        @NotBlank(message = "the confirm password must not be blank")
        @Size(min = 8, message = "confirm password's length must be at least 8")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "the password must contain at " +
                "least one number, at least one lowercase letter, at least one uppercase letter, must not contain spaces")
        String confirmPassword,

        @NotBlank(message = "the nickname must not be blank")
        @Size(min = 2, message = "nickname's length must be at least 2")
        @Pattern(regexp = "^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\\- ]{2,}", message = "the nickname must be only characters " +
                "of the Latin and Ukrainian alphabets, apostrophes, hyphens and spaces")
        String nickname

) {
}
