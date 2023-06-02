package com.tuleninov.web.model.user.response;

import com.tuleninov.web.model.user.KnownAuthorityUI;
import com.tuleninov.web.model.user.UserUIStatus;

import java.time.OffsetDateTime;
import java.util.EnumSet;

/**
 * Record for the user response.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
public record UserUIResponse(

        long id,
        String email,
        String nickname,
        UserUIStatus status,
        OffsetDateTime createdAt,
        EnumSet<KnownAuthorityUI> authorities

) {
}
