/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceweb.controller.user;

import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.user.SaveUserRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.config.pagination.PaginationConfig;
import com.nixsolution.alextuleninov.marketplace.marketplaceweb.service.user.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller for the user.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@Controller
@RequestMapping("/admins/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get user page with parameters.
     *
     * @param request       the servlet container, which provide request information for HTTP servlets
     * @param model         holder for model attributes
     * @return              user page
     */
    @GetMapping()
    public String getUsersPage(HttpServletRequest request,
                               Model model) {
        var config = PaginationConfig.config(request);
        var usersUI = userService.findAll(PageRequest.of(config.page(), config.size()));

        model.addAttribute("users", usersUI);

        return "user/user";
    }

    /**
     * Get user update page.
     *
     * @param email         email of user
     * @param model         holder for model attributes
     * @return              user update page
     */
    @GetMapping("/{email}")
    public String getRegisterUpdatePage(@PathVariable(value = "email") String email,
                                        Model model) {
        var userUI= userService.findByEmail(email);

        model.addAttribute("user", userUI);

        return "register/register-update";
    }

    /**
     * Update user in database.
     *
     * @param id            id of user
     * @param request       request with category parameters
     * @return              user page
     */
    @PutMapping("/{id}")
    public String updateUser(@PathVariable(value = "id") int id,
                             @Valid @ModelAttribute("user") SaveUserRequest request) {
        userService.update(id, request);

        return "redirect:/admins/users";
    }

    /**
     * Delete user from database.
     *
     * @param email         email of user
     * @return              user page
     */
    @DeleteMapping("/{email}")
    public String deleteUser(@PathVariable(value = "email") String email) {
        userService.delete(email);

        return "redirect:/admins/users";
    }
}
