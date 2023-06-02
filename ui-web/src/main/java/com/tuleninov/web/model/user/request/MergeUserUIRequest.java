package com.tuleninov.web.model.user.request;

import com.tuleninov.web.model.constraints.NullableNotBlank;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public record MergeUserUIRequest(

        @NotNull(message = "the email must not be null")
        @Email(message = "the email must be a valid email string")
        String email,

        @NullableNotBlank(message = "the nickname must not be blank")
        String nickname

) {
}
