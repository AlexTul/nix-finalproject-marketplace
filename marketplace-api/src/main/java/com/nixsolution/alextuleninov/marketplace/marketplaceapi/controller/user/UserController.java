/*
 * Copyright (c) 2022
 * For NIX Solutions
 */
package com.nixsolution.alextuleninov.marketplace.marketplaceapi.controller.user;

import com.nixsolution.alextuleninov.marketplace.marketplacelib.data.user.SaveUserRequest;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.data.user.UserResponse;
import com.nixsolution.alextuleninov.marketplace.marketplaceapi.service.user.UserCRUD;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static com.nixsolution.alextuleninov.marketplace.marketplaceapi.exception.user.UserExceptions.userNotFound;

/**
 * Rest controller for the User.
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@RestController
public class UserController {

    private final UserCRUD userCRUD;

    public UserController(UserCRUD userCRUD) {
        this.userCRUD = userCRUD;
    }

    /**
     * Create the user in the database.
     *
     * @param request       request with user parameters
     * @param ucb           builder for UriComponents
     * @return              the user from database in response format
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> register(@Valid @RequestBody SaveUserRequest request, UriComponentsBuilder ucb) {
        UserResponse response = userCRUD.create(request);
        return ResponseEntity
                .created(ucb.path("/register/{id}").build(response.id()))
                .body(response);
    }

    /**
     * Get all users from database in response format with pagination information.
     *
     * @param pageable      abstract interface for pagination information
     * @return              all users from database in response format
     */
    @GetMapping(value = "/admins/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @PageableAsQueryParam
    public Page<UserResponse> getAll(@Parameter(hidden = true) Pageable pageable) {
        return userCRUD.findAll(pageable);
    }

    /**
     * Get the user by email from the database in response format.
     *
     * @param email         email of user
     * @return              the user from database in response format
     */
    @GetMapping(value = "/admins/users/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getCurrentUser(@PathVariable String email) {
        return userCRUD.findByEmail(email)
                .orElseThrow(() -> userNotFound(email));
    }

    /**
     * Get the user by id from the database in response format.
     *
     * @param id            id of user
     * @return              the user from database in response format
     */
    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponse getUserById(@PathVariable int id) {
        return userCRUD.findById(id)
                .orElseThrow(() -> userNotFound(id));
    }

    /**
     * Exists user by email in the database in boolean format.
     *
     * @param email         email of user
     * @return              true - if user exists in database and false - is user does not exist in database
     */
    @GetMapping(value = "/register/{email}")
    public boolean existsByEmail(@PathVariable String email) {
        return userCRUD.existsByEmail(email);
    }

    /**
     * Update the user in the database.
     *
     * @param id            id of goods
     * @param request       request with user parameters
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = "/admins/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable int id, @Valid @RequestBody SaveUserRequest request) {
        userCRUD.update(id, request);
    }

    /**
     * Delete the user in the database.
     *
     * @param email         email of user
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/admins/users/{email}")
    public void delete(@PathVariable String email) {
        userCRUD.deleteByEmail(email);
    }
}
