/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.user;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.model.user.CustomUserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Create a service for searching and authenticating users.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Return user with details (email, password, role).
     *
     * @param email        email of user
     * @return             user details
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserDetail(
                        userService.findByEmail(email));
    }
}
