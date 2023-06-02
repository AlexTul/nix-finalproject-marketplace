package com.tuleninov.serverapi.model.auth.request;

import javax.validation.constraints.NotNull;

public record RefreshTokenRequest(

        @NotNull String refreshToken

) {
}
