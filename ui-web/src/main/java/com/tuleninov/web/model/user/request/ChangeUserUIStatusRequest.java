package com.tuleninov.web.model.user.request;

import com.tuleninov.web.model.user.UserUIStatus;

import javax.validation.constraints.NotNull;

public record ChangeUserUIStatusRequest(

        @NotNull UserUIStatus status

) {
}
