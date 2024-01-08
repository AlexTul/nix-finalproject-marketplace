package com.tuleninov.serverapi.model.auth;

import com.tuleninov.serverapi.model.user.CustomUser;
import com.tuleninov.serverapi.model.user.KnownAuthority;
import com.tuleninov.serverapi.model.user.UserStatus;
import org.springframework.security.core.userdetails.User;

import java.util.EnumSet;

/**
 * Create our custom UserDetails.
 * Is a wrapper over an entity.
 */
public class CustomUserDetails extends User {

    private final CustomUser source;

    public CustomUserDetails(CustomUser source) {
        super(source.getEmail(),
                source.getPassword(),
                source.getStatus() == UserStatus.ACTIVE,
                true,
                true,
                true,
                EnumSet.copyOf(source.getAuthorities().keySet())
        );
        this.source = source;
    }

    public CustomUser getSource() {
        return source;
    }
}
