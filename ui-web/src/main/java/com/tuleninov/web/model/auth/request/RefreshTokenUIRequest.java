package com.tuleninov.web.model.auth.request;

import javax.validation.constraints.NotNull;

public record RefreshTokenUIRequest(@NotNull String refreshToken) {
}
