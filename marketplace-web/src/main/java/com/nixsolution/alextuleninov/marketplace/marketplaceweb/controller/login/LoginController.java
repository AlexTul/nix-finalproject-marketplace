/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.login;

import com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.CartCount;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the login page.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
public class LoginController {

    /**
     * Get login page.
     *
     * @return              login page
     */
    @GetMapping("/login")
    public String login() {
        CartCount.cart.clear();

        return "login";
    }
}
