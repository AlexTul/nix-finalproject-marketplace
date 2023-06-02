package com.tuleninov.serverapi.model.user.request;

import com.tuleninov.serverapi.model.user.UserStatus;

import javax.validation.constraints.NotNull;

public record ChangeUserStatusRequest(

        @NotNull UserStatus status

) {
}
