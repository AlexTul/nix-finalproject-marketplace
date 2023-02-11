/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.register;

import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.user.SaveUserRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller for the register page.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get register page with parameters.
     *
     * @return              index page
     */
    @GetMapping("/register")
    public String getRegisterPage() {
        return "register/register";
    }

    /**
     * Add user to database.
     *
     * @param request       request with category parameters
     * @return              goods page
     */
    @PostMapping("/register")
    public String registeredPost(@ModelAttribute("user") SaveUserRequest request,
                                 HttpServletRequest httpRequest,
                                 Model model) throws ServletException {
        if (userService.existsByEmail(request.email())) {
            model.addAttribute("error", "User exists");
            return "register/register";
        }
        userService.register(request);
        httpRequest.login(request.email(), request.password());

        return "redirect:/";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code,
                           Model model) {
        model.addAttribute("message", "User successfully activated");

        return "login";
    }
}
