package com.tuleninov.serverapi.model.user.request;

import com.tuleninov.serverapi.model.constraints.NullableNotBlank;

import javax.validation.constraints.Email;

public record MergeUserRequest(

        @Email(message = "the email must be a valid email string")
        String email,

        @NullableNotBlank(message = "the nickname must not be blank")
        String nickname

) {
}
